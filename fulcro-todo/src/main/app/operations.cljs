(ns app.operations
  (:require [fulcro.client.mutations :as m :refer [defmutation]]))

(defmutation toggle-item-checked?!
  [{:keys [id]}]
  (action [{:keys [state]}]
          (let [ident [:item/by-id id]
                current-item (get-in @state ident)
                updated-item (update current-item :item/checked? not)]
            (swap! state assoc-in ident updated-item))))

(defmutation add-item!
  [_]
  (action [{:keys [state]}]
          (swap! state
                 (fn [state]
                   (let [item {:db/id (random-uuid)
                               :item/added-at (.now js/Date)
                               :item/text (get-in state [:item-adder :item-adder/input-value])
                               :item/checked? false}
                         ident [:item/by-id (:db/id item)]]
                     (-> state
                         (assoc-in [:item-adder :item-adder/input-value] "")
                         (update-in [:item-list :item-list/items] #(-> % (conj ident) vec))
                         (assoc-in ident item)))))))

(defmutation delete-item!
  [{:keys [id]}]
  (action [{:keys [state]}]
          (swap! state
                 (fn [state]
                   (let [ident [:item/by-id id]]
                     (-> state
                         (update-in [:item-list :item-list/items] #(->> % (remove (partial = ident)) vec))
                         (update :item/by-id dissoc id)))))))

(defmutation update-input-value!
  [{:keys [input-value]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:item-adder :item-adder/input-value] input-value)))
