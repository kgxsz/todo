(ns app.ui
  (:require [app.operations :as ops]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]
            [fulcro.client.core :as fc]))

(defui ^:once ItemAdder
  static om/IQuery
  (query [this] [:item-adder/input-value])
  static fc/InitialAppState
  (initial-state [c params] {:item-adder/input-value ""})
  Object
  (render [this]
          (let [{:keys [item-adder/input-value]} (om/props this)
                {:keys [update-input-value!]} (om/get-computed this)
                valid-input-value? false]
            (dom/form
             #js {:className "item-adder"
                  :onSubmit #(.preventDefault %)}
             (dom/input
              #js {:className "item-adder__input"
                   :type "text"
                   :placeholder "add an item here"
                   :value input-value
                   :onChange #(update-input-value! {:input-value input-value})})
             (dom/input
              #js {:className (if valid-input-value?
                                "item-adder__button"
                                "item-adder__button item-adder__button--disabled")
                   :type "submit"
                   :value "add"
                   :disabled (not valid-input-value?)})))))

(def ui-item-adder (om/factory ItemAdder))

(defui ^:once Item
  static om/Ident
  (ident [this props] [:item/by-id (:db/id props)])
  static om/IQuery
  (query [this] [:db/id :item/added-at :item/text :item/checked?])
  static fc/InitialAppState
  (initial-state [c {:keys [id added-at text]}] {:db/id id
                                                 :item/added-at added-at
                                                 :item/text text
                                                 :item/checked? false})
  Object
  (render [this]
          (let [{:keys [db/id item/added-at item/text item/checked?]} (om/props this)
                {:keys [toggle-item-checked?! delete-item!]} (om/get-computed this)]
            (dom/li
             #js {:className "item"}

             (dom/button
              #js {:className "item__checkbox"
                   :onClick #(toggle-item-checked?! {:id id})}
              (dom/img
               #js {:className (if checked?
                                 "item__checkbox__sprite item__checkbox__sprite--shifted"
                                 "item__checkbox__sprite")
                    :alt "checkbox"
                    :src "images/checkbox-sprite.svg"}))

             (dom/div
              #js {:className "item__text"}
              text)

             (dom/button
              #js {:className "item__trash"
                   :onClick #(delete-item! {:id id})}
              (dom/img
               #js {:alt "trashx"
                    :src "images/trash-icon.svg"}))))))

(def ui-item (om/factory Item))

(defui ^:once ItemList
  static om/IQuery
  (query [this] [{:item-list/items (om/get-query Item)}])
  static fc/InitialAppState
  (initial-state [c params] {:item-list/items [(fc/get-initial-state Item {:id 1
                                                                           :added-at 1508175827181
                                                                           :text "hello"})
                                               (fc/get-initial-state Item {:id 2
                                                                           :added-at 1508175970713
                                                                           :text "world"})]})
  Object
  (render [this]
          (let [{:keys [item-list/items]} (om/props this)
                toggle-item-checked?! (fn [{:keys [id]}]
                             (om/transact! this `[(ops/toggle-item-checked?! {:id ~id})]))
                delete-item! (fn [{:keys [id]}]
                              (om/transact! this `[(ops/delete-item! {:id ~id})]))]
            (if (empty? items)
              (dom/div
               #js {:className "item-list"}
               (dom/div
                #js {:className "item-list__notice"}
                (dom/img
                 #js {:className "item-list__notice__icon"
                      :alt "smiley"
                      :src "images/smiley-icon.svg"})
                "There are no items"))

              (dom/div
               #js {:className "item-list"}
               (dom/div
                #js {:className "item-list__options"}
                (dom/div
                 #js {:className "item-list__options__divider"})
                (dom/button
                 #js {:className "item-list__options__sort"}
                 (dom/img
                  #js {:alt "sort"
                       :src "images/sort-icon.svg"})))

               (dom/ul
                nil
                (map
                 (fn [item]
                   (ui-item (om/computed item {:toggle-item-checked?! toggle-item-checked?!
                                               :delete-item! delete-item!})))
                 items)))))))

(def ui-item-list (om/factory ItemList))

(defui ^:once App
  static om/IQuery
  (query [this] [:ui/react-key
                 {:item-adder (om/get-query ItemAdder)}
                 {:item-list (om/get-query ItemList)}])
  static fc/InitialAppState
  (initial-state [c params] {:item-adder (fc/get-initial-state ItemAdder {})
                             :item-list (fc/get-initial-state ItemList {})})

  Object
  (render [this]
          (let [{:keys [ui/react-key item-adder item-list]} (om/props this)
                update-input-value! (fn [{:keys [input-value]}]
                                      (om/transact! this `[(ops/update-input-value! {:input-value ~input-value})]))]
            (dom/div
             #js {:key react-key
                  :className "app"}
             (dom/div
              #js {:className "app__header"}
              (dom/div
               #js {:className "app__header__title"}
               "Todo List")
              (dom/div
               #js {:className "app__header__subtitle"}
               "Developed with Fulcro"))
             (dom/div
              #js {:className "app__body"}
              (dom/div
               #js {:className "app__body__divider"})
              (ui-item-adder (om/computed item-adder {:update-input-value! update-input-value!}))
              (dom/div
               #js {:className "app__body__divider"})
              (ui-item-list item-list))))))
