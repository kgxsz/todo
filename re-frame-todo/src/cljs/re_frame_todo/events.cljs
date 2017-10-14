(ns re-frame-todo.events
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [re-frame-todo.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :update-input-value
 (fn  [db [_ input-value]]
   (-> db
       (assoc :input-value input-value))))

(re-frame/reg-event-db
 :add-item-to-item-list
 (fn [db [_ added-at]]
   (let [item {:added-at added-at
               :text (:input-value db)
               :checked? false}]
     (-> db
         (assoc :input-value "")
         (assoc-in [:items-by-added-at added-at] item)
         (update-in [:item-list] (partial cons added-at))))))


