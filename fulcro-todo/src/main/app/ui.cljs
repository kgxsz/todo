(ns app.ui
  (:require [fulcro.client.core :as fc]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]))

(defonce app (atom (fc/new-fulcro-client)))

(defui App
  Object
  (render [this]
          (dom/div nil "Hello World.")))
