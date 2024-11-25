(ns sql
  (:require
   [babashka.process :refer [shell]]
   [clojure.string :as str]
   [nextjournal.clerk :as clerk]
   [utils]
   [sql]))

(def viewer
  {:render-fn
   '(fn [data _]
      [nextjournal.clerk.render/with-dynamic-import
       {:module "https://unpkg.com/@highlightjs/cdn-assets@11.10.0/es/highlight.min.js"}
       (fn [hljs]
         [nextjournal.clerk.render/with-dynamic-import
          {:module "https://unpkg.com/@highlightjs/cdn-assets@11.10.0/es/languages/pgsql.min.js"}
          (fn [hlsql]
            (-> hljs (.-default) (.registerLanguage "sql" (.-default hlsql)))
            (let [parser (js/DOMParser.)
                  highlighted (-> hljs
                                  (.-default)
                                  (.highlight data (clj->js {:language "sql"})))]
              [:<>
               [:link
                {:rel "stylesheet"
                 :href "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.10.0/styles/color-brewer.min.css"}]
               [:div.cm-content.whitespace-pre.font-mono.bg-gray-50.p-8
                {:dangerouslySetInnerHTML {:__html (.-value highlighted)}}]]))])])})

(defn bb [& args]
  (:out (apply shell {:out :string :dir "/Volumes/libext/musicbrainz"} "bb" args)))

(defn sql [symbol]
  ^{:nextjournal.clerk/width :full}
  (clerk/with-viewer viewer
    (-> (bb "print-query" (pr-str symbol)))))

(defn sql-str [s]
  ^{:nextjournal.clerk/width :full}
  (clerk/with-viewer viewer s))

; (def sugar-man-ilike
;   "SELECT 
;     recording.name AS recording_name,
;     artist.name AS artist_name,
;     ROUND(recording.length / 60000.0, 2) AS length_minutes
; FROM 
;     recording
; JOIN 
;     artist ON recording.artist_credit = artist.id
; WHERE  (recording.name ILIKE '%Sugar%' OR recording.name ILIKE '%Man%')
; ORDER BY 
;     recording_name;
; ")

(def sugar-man-pg-search
  "
CREATE EXTENSION pg_search;

SELECT name,
paradedb.score(id)   --- BM25 score column
FROM recording
WHERE name
@@@ '\"Sugar Man\"'    --- query on RHS of operator
ORDER BY score DESC; --- order by generated score")

(def sugar-man-ilike
  "
CREATE INDEX recording_name_gin_trgm_idx ON recording USING gin (name gin_trgm_ops);

SELECT * FROM recording WHERE name ILIKE '%Sugar Man%';
  ")

(def artist-create-bm25-tokenizers
  "
CALL paradedb.create_bm25(
  index_name => 'artist_search_idx',
  table_name => 'artist',
  key_field => 'id',
  text_fields => 
    paradedb.field('name', tokenizer => paradedb.tokenizer('default')) ||
    paradedb.field('sort_name',
      tokenizer => paradedb.tokenizer(
        'ngram', min_gram => 2, max_gram => 4, prefix_only => false
      )
    ) ||
    paradedb.field('comment',
      tokenizer => paradedb.tokenizer('default', stemmer => 'english' )
    )
);
")

;; (db! (db/artist-sqlvec))
;; (db! (db/artist-active-sqlvec))
;; (db! (db/top-table-schemas-sqlvec))
(def sugar-man-score-results
  [["name" "score"]
   ["Sugar Man" 	                      19.720665]
   ["Sugar Man Dub"                     18.051449]
   ["Sugar Man (live)"	                18.051449]
   ["Sugar Man (instrumental)"	        18.051449]
   ["Sugar Man (remix)"	                18.051449]
   ["Sugar Man (radio edit)"	          16.642757]
   ["Sugar Man’s Blues"	                16.642757]
   ["Sugar Man (demo song)"	            16.642757]
   ["Vendetta: The Sugar Man"	          16.642757]
   ["Sugar Man No.2"	                  16.642757]
   ["Sugar Man (Generik Remix)"	        16.642757]
   ["Sugar Man (Original Mix)"	        16.642757]
   ["Sugar Man (Indopepsychics Remix)"	16.642757]])
