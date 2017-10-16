(ns app.ui
  (:require [fulcro.client.core :as fc]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]))

(defonce app (atom (fc/new-fulcro-client)))

(defui App
  Object
  (render [this]
          (dom/div
           #js {:className "app"}
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
            (dom/div
             #js {:className "app__body__divider"})))))
