(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer [defui]]
            [fulcro.client.core :as fc]))

(defui ^:once ItemAdder
  static
  fc/InitialAppState
  (initial-state [c params] {:input-value ""
                             :valid-input-value? false})
  Object
  (render [this]
          (let [{:keys [input-value valid-input-value?]} (om/props this)]
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

(defui ^:once ItemList
  static
  fc/InitialAppState
  (initial-state [c params] {:items-by-added-at {1508175827181 {:added-at 1508175827181
                                                                :text "hello"
                                                                :checked? false}
                                                 1508175970713 {:added-at 1508175970713
                                                                :text "world"
                                                                :checked? true}}
                             :item-list '(1508175827181 1508175970713)})
  Object
  (render [this]
          (let [{:keys [items-by-added-at item-list]} (om/props this)]
            (if (empty? item-list)
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
                 #js {:className "item-list__options__sort"
                      :alt "smiley"
                      :src "images/smiley-icon.svg"}
                 (dom/img
                  #js {:alt "sort"
                       :src "images/sort-icon.svg"}))))))))

(def ui-item-list (om/factory ItemList))

(defui ^:once App
  static
  fc/InitialAppState
  (initial-state [c params] {:item-adder (fc/get-initial-state ItemAdder {})
                             :item-list (fc/get-initial-state ItemList {})})

  Object
  (render [this]
          (let [{:keys [ui/react-key]} (om/props this)
                {:keys [item-adder item-list]} (fc/get-initial-state App {})]
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
