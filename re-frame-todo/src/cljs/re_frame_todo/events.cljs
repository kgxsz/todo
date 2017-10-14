(ns re-frame-todo.events
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [re-frame-todo.db :as db]
            [re-frame-todo.schema :as schema]
            [medley.core :as medley]))

(def schema-interceptor (re-frame/after (fn [db]
                                          (when-not (spec/valid? ::schema/db db)
                                            (throw (ex-info (str "spec check failed: " (spec/explain-str ::schema/db db)) {}))))))

(re-frame/reg-event-db
 :initialize-db
 [schema-interceptor]
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :update-input-value
 [schema-interceptor]
 (fn  [db [_ input-value]]
   (-> db
       (assoc :input-value input-value))))

(re-frame/reg-event-db
 :add-item-to-item-list
 [schema-interceptor]
 (fn [db [_ added-at]]
   (let [item {:added-at added-at
               :text (:input-value db)
               :checked? false}
         sort-by-desc-added-at? (:sort-by-desc-added-at? db)]
     (-> db
         (assoc :input-value "")
         (assoc-in [:items-by-added-at added-at] item)
         (update-in [:item-list] (partial cons added-at))
         (update :item-list #(sort (if sort-by-desc-added-at? > <) %))))))

(re-frame/reg-event-db
 :toggle-item-checked?
 [schema-interceptor]
 (fn [db [_ added-at]]
   (update-in db [:items-by-added-at added-at :checked?] not)))

(re-frame/reg-event-db
 :delete-item
 [schema-interceptor]
 (fn [db [_ added-at]]
   (-> db
       (medley/dissoc-in [:items-by-added-at added-at])
       (update :item-list #(remove (partial = added-at) %)))))

(re-frame/reg-event-db
 :toggle-sort-item-by-desc-added-at?
 [schema-interceptor]
 (fn [db [_ added-at]]
   (let [sort-by-desc-added-at? (not (:sort-by-desc-added-at? db))]
     (-> db
         (assoc :sort-by-desc-added-at? sort-by-desc-added-at?)
         (update :item-list #(sort (if sort-by-desc-added-at? > <) %))))))
