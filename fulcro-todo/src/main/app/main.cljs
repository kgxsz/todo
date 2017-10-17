(ns app.main
  (:require [app.ui :as ui]
            [app.core :as core]
            [fulcro.client.core :as fc]))

(reset! core/app (fc/mount @core/app ui/App "app"))
