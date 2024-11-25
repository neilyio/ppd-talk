(ns user
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require [nextjournal.clerk :as clerk]
            [clojure.string]))

(defn start [_]
  (clerk/serve! {:watch-paths ["src", "notebooks"]
                 :show-filter-fn #(clojure.string/starts-with? % "notebooks")}))
