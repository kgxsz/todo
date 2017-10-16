(ns cljs.user
  (:require
   [app.ui :refer [app App]]
   [fulcro.client.core :as fc]))

(defn refresh [] (swap! app fc/mount App "app"))

(refresh)
