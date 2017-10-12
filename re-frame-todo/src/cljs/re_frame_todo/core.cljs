(ns re-frame-todo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-todo.events]
            [re-frame-todo.subs]
            [re-frame-todo.views :as views]
            [re-frame-todo.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
