(ns cljs.user
  (:require
   [app.ui :refer [app]]
   [fulcro.client.core :as fc]))

(defn refresh [] (swap! app fc/mount ui-app "app"))

(refresh)
