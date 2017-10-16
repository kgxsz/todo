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
                   :value input-value})
             (dom/input
              #js {:className (if valid-input-value?
                                "item-adder__button"
                                "item-adder__button item-adder__button--disabled")
                   :type "submit"
                   :value "add"
                   :disabled (not valid-input-value?)})))))

(def ui-item-adder (om/factory ItemAdder))

(defui ^:once App
  static
  fc/InitialAppState
  (initial-state [c params] {:item-adder (fc/get-initial-state ItemAdder {})})

  Object
  (render [this]
          (let [{:keys [ui/react-key]} (om/props this)
                {:keys [item-adder]} (fc/get-initial-state App {})]
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
               #js {:className "app__body__divider"}))))))
