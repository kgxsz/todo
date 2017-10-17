(ns app.schema
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as str]))

(spec/def :item/text (spec/and string? (complement str/blank?)))
