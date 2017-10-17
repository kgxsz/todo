(ns cljs.user
  (:require [app.ui :as ui]
            [app.core :as core]
            [fulcro.client.core :as fc]))

(defn refresh [] (swap! core/app fc/mount ui/App "app"))

(refresh)
