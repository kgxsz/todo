(ns re-frame.events
  (:require [re-frame.core :as re-frame]
            [re-frame.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
