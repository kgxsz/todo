(ns app.ui
  (:require [fulcro.client.core :as fc]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]))

; Create an application
(defonce app-1 (atom (fc/new-fulcro-client)))

; Create a simple UI
(defui Root
  Object
  (render [this]
          (dom/div nil "Hello World.")))
