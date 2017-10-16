(ns cljs.user
  (:require
   [app.ui :refer [app-1 Root]]
   [fulcro.client.core :as fc]))

; so figwheel can call it on reloads. Remounting just forces a UI refresh.
(defn refresh [] (swap! app-1 fc/mount Root "app-1"))

; for initial mount
(refresh)
