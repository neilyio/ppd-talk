^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(ns slides.main
  (:require
   [clojure.java.io :as io]
   [nextjournal.clerk :as clerk]
   [nextjournal.clerk-slideshow :as slideshow]
   [sql]
   [utils]
   [slides.who-is-paradedb :as slides]))

^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(require '[slides.who-is-paradedb :as slides] :reload)
^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(require '[sql] :reload)
^{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(require '[nextjournal.clerk-slideshow] :reload)

{:nextjournal.clerk/visibility {:code :hide :result :show}}

^{::clerk/visibility {:code :hide :result :hide}}
(clerk/add-viewers! [slideshow/viewer])

;; ---
;; # Don't Stop Retrievin'
;; Building lightning-fast search in Postgres.

;; ---
(slides/title
 :title [:<> "Who is" [:span.ml-3.translate-y-1 (slides/logo)] "?"]
 :lyric "Who are you? Who? Who?"
 :artist "-- The Who")

;; We are a team of four based out of New York, North Carolina, and California.
;; **Phil**, **Ming**, **Neil**, and **Eric**.
(let [class "h-1/6 w-1/6 overflow-hidden rounded-xl"]
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:div.flex.w-full.justify-center.space-x-12
    [:div {:className class} (clerk/image (io/file "resources/img/phil.png"))]
    [:div {:className class} (clerk/image (io/file "resources/img/ming.jpeg"))]
    [:div {:className class} (clerk/image (io/file "resources/img/neil.jpeg"))]
    [:div {:className class} (clerk/image (io/file "resources/img/eric.jpeg"))]]))

;; ---
;; We're building the fastest full-text search engine in the world. **Right into Postgres**.

(slides/iframe-homepage)

;; ---
(slides/title
 :title "Why are we building this?"
 :lyric "I remember, I remember when I lost my mind"
 :artist "Gnarls Barkley")

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div
  [:div.grid.grid-cols-3.gap-x-16
   [:div.col-span-1.mt-3 (slides/iframe-changelog)]
   [:div.col-span-2
    [:p "We love Postgres, and we're making a big bet on its"
     " extensibility towards new workloads."]
    [:p "With the Postgres extension APIs, the possibilities are limitless. We make use of:"]
    [:ul
     [:li "Index Access Methods"]
     [:li "Table Access Methods"]
     [:li "Custom Scan API"]
     [:li "Custom Resource Manager API"]
     [:li "Background Workers"]
     [:li "Block Storage Interface"]]]]])

;; ---
(slides/title
 :title "A musical database"
 :lyric "I still haven't found what I'm looking for"
 :artist "Bono")

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div
  [:div.grid.grid-cols-3.gap-x-16
   [:div
    [:p "The world's musical catalog is backed by Postgres."]

    [:div.max-w-80
     [:div.font-mono.flex.justify-between [:b "Artists: "] "2,475,667"]
     [:div.font-mono.flex.justify-between [:b "Recordings: "] "33,499,949"]
     [:div.font-mono.flex.justify-between [:b "Tracks: "] "47,500,138"]
     [:div.font-mono.flex.justify-between [:b "Instruments: "] "1,048"]
     [:div.font-mono.flex.justify-between [:b "Genres: "] "1,988"]]]

   [:div.col-span-2.mt-3 (slides/iframe-musicbrainz)]]])

;; ---
(slides/title
 :title "Searching for Sugar Man"
 :lyric "Sugar man, you're the answer that makes my questions disappear"
 :artist "Rodriguez")

;; Retrieves a list of recordings with titles containing the words `Sugar` or `Man`.
^{:nextjournal.clerk/width :full}
(sql/sql-str sql/sugar-man-ilike)

;; ---
;; Our `ILIKE` query takes about `2.3 seconds` to complete, and mixes exact results like `Sugar Man` with:
(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.flex.justify-center
  (clerk/table (clerk/use-headers
                [["name"]
                 ["SUGAR MAN"]
                 ["Schwarzer Kaffee aus San Juan (The Sugar Man's Song)"]
                 ["Sugar Mandala (Dj Kuznetsoff mashup)"]
                 ["Sugar Man"]
                 ["Sugar Man Blues - Part 1"]
                 ["Sugar Man Blues - Part 2"]
                 ["Vendetta: The Sugar Man"]
                 ["You (From Sugar Man 2, Pt. 2)"]
                 ["Sugar Man’s Blues"]
                 ["Sugar Mandala (Dj Kuznetsoff mashup)"]]))])

;; `ILIKE` is not the right tool for the job here. A key problem to solve with full-text search is making sure that the user gets *the best* matches first. To ensure that, we need a **ranking algorithm**.

;; ---
(slides/title
 :title "BM25? UB40? SUM41? U2?"
 :lyric "I still haven't found what I'm looking for"
 :artist "Bono")

;; In `pg_search`, we use the `BM25` or `Best Matching 25` algorithm for ranking results.

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.w-full.flex.justify-center
  (clerk/html (slurp (io/file "resources/img/bm25.svg")))])

(clerk/md
 "
 •	**TF(q_i, D)**: Term frequency of  q_i  in document  D .

 •	**IDF(q_i)**: Inverse document frequency of  q_i .

 •	**k_1 and b**: Tuning parameters to adjust term frequency saturation and document length normalization.

 •	**|D|**: Length of the document  D .

 •	**avgD**: Average document length across the document set.
")

;; ---
(slides/title
 :title "pg_searching for Sugar Man"
 :lyric "Smooth operator"
 :artist "Sade")

;; A pg_search `@@@` query returns a new `score` column, which we can `ORDER BY`.
;;
;; The result set is much more reflective of the input query, and only took **75ms**
;; for the same ~125 rows.

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.flex.space-x-16.justify-center
  (clerk/table (clerk/use-headers sql/sugar-man-score-results))
  (sql/sql-str sql/sugar-man-pg-search)])

;; ---
;; MusicBrainz is a very "relational" dataset. We want to be able to work with data like this
;; in the shape that it is stored.

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.flex.justify-center
  [:div
   {:class "w-1/3"}
   (clerk/image (io/file "resources/img/mb_schema.png"))]])

;; ---
(slides/title
 :title "Creating a BM25 index"
 :lyric "Matchmaker, matchmaker, make me a match"
 :artist "Fiddler on the Roof")

(slides/iframe-quickstart)

;; ---
(slides/title
 :title  "Choosing a tokenizer"
 :lyric  "R-E-S-P-E-C-T"
 :artist "Aretha Franklin")

 ;; Tokenizers determine how text is split up when indexed. Picking the right tokenizer is crucial for returning the results that you want.
 ;; 
 ;; Tokenizer options include:
 ;; - **Stemming** for English, Français, العربية, Deutsch, Italiano, Português, Русский, Español, and more.
 ;; - **Ngrams** for indexing `per erm rmu mut uta tai ion ons` of characters.
 ;; - **Regex** for splitting text based on a `pa(tt)ern`.
 ;; - **Whitespace** for fast, predicatable `tokenization on words`.

;; ---
;; Selecting tokenizers for fields happens at index build time, so its important to build an
;; intuition for how they will affect your search.
^{:nextjournal.clerk/width :full}
(sql/sql-str sql/artist-create-bm25-tokenizers)

;; ---

^{:nextjournal.clerk/width :full}
(sql/sql-str
 "
SELECT paradedb.tokenize(paradedb.tokenizer(
        'ngram', min_gram => 2, max_gram => 4, prefix_only => false
	),
	'Sgt. Pepper''s Lonely Hearts Club Band (Deluxe Edition)'
); --  sgt pepper s lone heart club band delux edit
 ")

^{:nextjournal.clerk/width :full}
(sql/sql-str
 "
SELECT paradedb.tokenize(
	paradedb.tokenizer(
		'default',
		stemmer => 'English'
	),
	'Sgt. Pepper''s Lonely Hearts Club Band (Deluxe Edition)'
) -- sg sgt sgt. gt gt. gt. t. t. t  p pe pep pe pep pepp ep epp eppe pp ppe pper pe per per' er
-- er' er's r' r's r's 's 's 's s s s l lo lon lo lon lone on one onel ne nel nely el ely ely ly ly
-- ly y y y h he hea he hea hear ea ear eart ar art arts rt rts rts ts c cl clu cl clu club lu lub
-- lub ub ub ub b b b b ba ban ba ban band an and and nd nd de del de del delu el elu elux lu lux
-- luxe ux uxe uxe xe xe xe e e e e e ed e ed edi ed edi edit di dit diti it iti itio ti tio tion
-- io ion ion on

")

;; ---
(slides/title
 :title  "Choosing a query"
 :lyric  "Ask me why"
 :artist "The Beatles")

(slides/iframe-range)

;; ---
(slides/title
 :title "The architecture of pg_search"
 :lyric "Well, how did I get here?"
 :artist "Talking Heads")

;; Let's take a look at `pg_search` under the hood. Here, we visualize the flow of data
;; from the client's query, through the index, and back with the result.

(slides/iframe-figma-height-slider)
(slides/iframe-figma-architecture)

;; ---
(slides/title
 :title "Core technology in pg_search"
 :lyric "You are the wind beneath my wings"
 :artist "Bette Midler")

;; Our work came to life because of incredible contributions by many the Rust and Postgres ecosystems.

;; `tantivy` is the incredibly fast search index library that powers `pg_search`.
;; 
;; `pgrx` is a groundbreaking framework for building Postgres extensions in Rust.

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.flex.items-center
  (clerk/image (io/file "resources/img/tantivy.png"))
  (clerk/image (io/file "resources/img/pgrx.png"))])

;; ---
;; ## Thank you.
;; It's a privilege getting to work in the Postgres ecosytem. The community's dedication to 
;; sharing, learning, and innovating inspires us every day.

(clerk/html
 {:nextjournal.clerk/width :full}
 [:div.flex.justify-center.mt-16 (slides/logo)])

