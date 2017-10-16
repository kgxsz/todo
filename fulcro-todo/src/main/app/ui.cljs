(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer [defui]]))

(defui ^:once ItemAdder
  Object
  (render [this]
          (dom/form
           #js {:className "item-adder"
                :onSubmit #(.preventDefault %)}
           (dom/input
            #js {:className "item-adder__input"
                 :type "text"
                 :value "hey"})
           (dom/input
            #js {:className "item-adder__button"
                 :type "submit"
                 :value "add"}))))

(def ui-item-adder (om/factory ItemAdder))

(defui ^:once App
  Object
  (render [this]
          (let [{:keys [ui/react-key]} (om/props this)]
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
              (ui-item-adder)
              (dom/div
               #js {:className "app__body__divider"}))))))
