(ns app.schema
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as str]))

(spec/def :db/id int?)

(spec/def :item/added-at int?)

(spec/def :item/text (spec/and string?
                               (complement str/blank?)))

(spec/def :item/checked? boolean?)

(spec/def :item/by-id (spec/keys :req [:db/id :item/added-at :item/text :item/checked?]))

(spec/def ::items-by-added-at (spec/and map?
                                        (spec/map-of ::added-at ::item)))

(spec/def ::item-list (spec/coll-of ::added-at))

(spec/def :item-adder/input-value string?)

(spec/def ::sort-by-desc-added-at? boolean?)
