(ns re-frame-todo.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :item-list
 (fn [db]
   (mapv #(get-in db [:items-by-added-at %]) (:item-list db))))

(re-frame/reg-sub
 :input-value
 (fn [db]
   (:input-value db)))

(re-frame/reg-sub
 :sort-by-desc-added-at
 (fn [db]
   (:sort-by-desc-added-at db)))
