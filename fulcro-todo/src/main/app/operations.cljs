(ns app.operations
  (:require [fulcro.client.mutations :as m :refer [defmutation]]))

(defmutation toggle-item-checked?!
  [{:keys [id]}]
  (action [{:keys [state]}]
          (let [ident [:item/by-id id]
                current-item (get-in @state ident)
                updated-item (update current-item :item/checked? not)]
            (swap! state assoc-in ident updated-item))))

(defmutation delete-item!
  [{:keys [id]}]
  (action [{:keys [state]}]
          (let [ident [:item/by-id id]
                current-items (get-in @state [:item-list :item-list/items])
                updated-items (vec (remove #(= ident %) current-items))]
            (swap! state assoc-in [:item-list :item-list/items] updated-items))))

(defmutation update-input-value!
  [{:keys [input-value]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:item-adder :item-adder/input-value] input-value)))
