(ns re-frame-todo.events
  (:require [re-frame.core :as re-frame]
            [re-frame-todo.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
