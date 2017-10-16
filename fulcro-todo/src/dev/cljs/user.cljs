(ns cljs.user
  (:require
   [app.ui :refer [app]]
   [fulcro.client.core :as fc]))

; so figwheel can call it on reloads. Remounting just forces a UI refresh.
(defn refresh [] (swap! app fc/mount ui-app "app"))

; for initial mount
(refresh)
