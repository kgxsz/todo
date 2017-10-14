(ns re-frame-todo.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :item-list
 (fn [db _]
   (:item-list db)))

(re-frame/reg-sub
 :item
 (fn [db [_ added-at]]
   (get-in db [:items-by-added-at added-at])))

(re-frame/reg-sub
 :input-value
 (fn [db _]
   (:input-value db)))

(re-frame/reg-sub
 :sort-by-desc-added-at
 (fn [db _]
   (:sort-by-desc-added-at db)))
