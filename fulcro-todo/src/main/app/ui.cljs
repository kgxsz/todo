(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer [defui]]
            [fulcro.client.core :as fc]))

(defui ^:once ItemAdder
  static
  fc/InitialAppState
  (initial-state [c params] {:item-adder/input-value ""})
  Object
  (render [this]
          (let [{:keys [item-adder/input-value]} (om/props this)
                valid-input-value? false]
            (dom/form
             #js {:className "item-adder"
                  :onSubmit #(.preventDefault %)}
             (dom/input
              #js {:className "item-adder__input"
                   :type "text"
                   :placeholder "add an item here"
                   :value input-value})
             (dom/input
              #js {:className (if valid-input-value?
                                "item-adder__button"
                                "item-adder__button item-adder__button--disabled")
                   :type "submit"
                   :value "add"
                   :disabled (not valid-input-value?)})))))

(def ui-item-adder (om/factory ItemAdder))

(defui ^:once Item
  static
  fc/InitialAppState
  (initial-state [c {:keys [added-at text]}] {:item/added-at added-at
                                              :item/text text
                                              :item/checked? false})
  Object
  (render [this]
          (let [{:keys [item/added-at item/text item/checked?]} (om/props this)]
            (dom/li
             #js {:className "item"}

             (dom/button
              #js {:className "item__checkbox"}
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
              #js {:className "item__trash"}
              (dom/img
               #js {:alt "trashx"
                    :src "images/trash-icon.svg"}))))))

(def ui-item (om/factory Item))

(defui ^:once ItemList
  static
  fc/InitialAppState
  (initial-state [c params] {:item-list/items [(fc/get-initial-state Item {:added-at 1508175827181
                                                                           :text "hello"
                                                                           :checked? false})
                                               (fc/get-initial-state Item {:added-at 1508175970713
                                                                           :text "world"
                                                                           :checked? true})]})
  Object
  (render [this]
          (let [{:keys [item-list/items]} (om/props this)]
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
                (map ui-item items)))))))

(def ui-item-list (om/factory ItemList))

(defui ^:once App
  static
  fc/InitialAppState
  (initial-state [c params] {:app/item-adder (fc/get-initial-state ItemAdder {})
                             :app/item-list (fc/get-initial-state ItemList {})})

  Object
  (render [this]
          (let [{:keys [ui/react-key]} (om/props this)
                {:keys [app/item-adder app/item-list]} (fc/get-initial-state App {})]
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
              (ui-item-adder item-adder)
              (dom/div
               #js {:className "app__body__divider"})
              (ui-item-list item-list))))))
