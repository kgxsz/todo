(ns app.operations
  (:require [fulcro.client.mutations :as m :refer [defmutation]]))

(defmutation delete-item
  [{:keys [id]}]
  (action [{:keys [state]}]
          (let [ident-to-remove [:item/by-id id]
                strip-fk (fn [old-fks]
                           (vec (remove #(= ident-to-remove %) old-fks)))]
            (swap! state update-in [:item-list :item-list/items] strip-fk))))
