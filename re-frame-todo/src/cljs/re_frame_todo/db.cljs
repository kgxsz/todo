(ns re-frame-todo.db)

(def default-db
  {:items-by-added-at {}
   :item-list []
   :input-value ""
   :sort-by-desc-added-at? true})
