(ns re-frame-todo.schema
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as str]))

(spec/def ::added-at int?)

(spec/def ::text (spec/and string?
                           (complement str/blank?)))

(spec/def ::checked? boolean?)

(spec/def ::item (spec/keys :req-un [::added-at
                                     ::text
                                     ::checked?]))

(spec/def ::items-by-added-at (spec/and map?
                                        (spec/map-of ::added-at ::item)))

(spec/def ::item-list (spec/coll-of ::added-at))

(spec/def ::input-value string?)

(spec/def ::sort-by-desc-added-at boolean?)

(spec/def ::db (spec/keys :req-un [::items-by-added-at
                                   ::item-list
                                   ::input-value
                                   ::sort-by-desc-added-at]))
