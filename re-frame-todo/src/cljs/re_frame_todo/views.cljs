(ns re-frame-todo.views
  (:require [re-frame.core :as re-frame]))

(defn app []
  (let [input-value (re-frame/subscribe [:item-list])]
    (fn []
      [:div.app
       [:div.app__header
        [:div.app__header__title "Todo List"]
        [:div.app__header__subtitle "Developed with Re-Frame"]]
       [:div.app__body
        [:div.app__body__divider]
        [:div.app__body__divider]]])))
