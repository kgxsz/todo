(ns re-frame-todo.db)

(def default-db
  {:items-by-added-at {1507978708423 {:added-at 1507978708423
                                      :text "first item"
                                      :checked? true}
                       1507978691274 {:added-at 1507978691274
                                      :text "second item"
                                      :checked? false}}
   :item-list [1507978708423
               1507978691274]
   :input-value "hello"
   :sort-by-desc-added-at? false})
