{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(ns slides.who-is-paradedb
  (:require
   [clojure.java.io :as io]
   [nextjournal.clerk :as clerk]))

(defn title [& {:keys [title lyric artist]}]
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:div.flex.items-baseline.space-x-8 {:class "flex-col lg:flex-row"}
    [:h2.flex.items-baseline title]
    [:span.flex.space-x-2.text-base.opacity-70
     (when lyric [:p.m-0 "\"" lyric "\""])  (when artist [:p.m-0.whitespace-nowrap
                                                          "-- " artist])]]))

(defn logo [& {:keys [class]}]
  (clerk/html
   [:svg.w-64
    {:viewBox "0 0 639 88",
     :fill "none",
     :xmlns "http://www.w3.org/2000/svg",
     :class (or class "w-32")}
    [:mask
     {:id "mask0_141_5",
      :style {:mask-type "alpha"},
      :maskunits "userSpaceOnUse",
      :x "0",
      :y "0",
      :width "639",
      :height "82"}
     [:rect {:width "639", :height "82", :fill "#D9D9D9"}]]
    [:g
     {:mask "url(#mask0_141_5)"}
     [:path
      {:d
       "M182.23 4.49999C189.3 4.49999 195.25 6.88 200.08 11.64C204.91 16.4 207.325 22.245 207.325 29.175C207.325 36.105 204.91 41.95 200.08 46.71C195.25 51.47 189.3 53.85 182.23 53.85H169.315V78H154.825V4.49999H182.23ZM182.23 40.305C185.31 40.305 187.865 39.255 189.895 37.155C191.925 34.985 192.94 32.325 192.94 29.175C192.94 25.955 191.925 23.295 189.895 21.195C187.865 19.095 185.31 18.045 182.23 18.045H169.315V40.305H182.23ZM252.542 25.5H266.087V78H252.542V71.805C248.482 76.915 242.777 79.47 235.427 79.47C228.427 79.47 222.407 76.81 217.367 71.49C212.397 66.1 209.912 59.52 209.912 51.75C209.912 43.98 212.397 37.435 217.367 32.115C222.407 26.725 228.427 24.03 235.427 24.03C242.777 24.03 248.482 26.585 252.542 31.695V25.5ZM227.552 62.46C230.282 65.19 233.747 66.555 237.947 66.555C242.147 66.555 245.612 65.19 248.342 62.46C251.142 59.66 252.542 56.09 252.542 51.75C252.542 47.41 251.142 43.875 248.342 41.145C245.612 38.345 242.147 36.945 237.947 36.945C233.747 36.945 230.282 38.345 227.552 41.145C224.822 43.875 223.457 47.41 223.457 51.75C223.457 56.09 224.822 59.66 227.552 62.46ZM291.915 34.53C293.175 31.17 295.24 28.65 298.11 26.97C301.05 25.29 304.305 24.45 307.875 24.45V39.57C303.745 39.08 300.035 39.92 296.745 42.09C293.525 44.26 291.915 47.865 291.915 52.905V78H278.37V25.5H291.915V34.53ZM353.748 25.5H367.293V78H353.748V71.805C349.688 76.915 343.983 79.47 336.633 79.47C329.633 79.47 323.613 76.81 318.573 71.49C313.603 66.1 311.118 59.52 311.118 51.75C311.118 43.98 313.603 37.435 318.573 32.115C323.613 26.725 329.633 24.03 336.633 24.03C343.983 24.03 349.688 26.585 353.748 31.695V25.5ZM328.758 62.46C331.488 65.19 334.953 66.555 339.153 66.555C343.353 66.555 346.818 65.19 349.548 62.46C352.348 59.66 353.748 56.09 353.748 51.75C353.748 47.41 352.348 43.875 349.548 41.145C346.818 38.345 343.353 36.945 339.153 36.945C334.953 36.945 331.488 38.345 328.758 41.145C326.028 43.875 324.663 47.41 324.663 51.75C324.663 56.09 326.028 59.66 328.758 62.46ZM419.371 4.49999H432.916V78H419.371V71.805C415.381 76.915 409.711 79.47 402.361 79.47C395.291 79.47 389.236 76.81 384.196 71.49C379.226 66.1 376.741 59.52 376.741 51.75C376.741 43.98 379.226 37.435 384.196 32.115C389.236 26.725 395.291 24.03 402.361 24.03C409.711 24.03 415.381 26.585 419.371 31.695V4.49999ZM394.381 62.46C397.181 65.19 400.681 66.555 404.881 66.555C409.081 66.555 412.546 65.19 415.276 62.46C418.006 59.66 419.371 56.09 419.371 51.75C419.371 47.41 418.006 43.875 415.276 41.145C412.546 38.345 409.081 36.945 404.881 36.945C400.681 36.945 397.181 38.345 394.381 41.145C391.651 43.875 390.286 47.41 390.286 51.75C390.286 56.09 391.651 59.66 394.381 62.46ZM456.748 57.315C458.568 63.895 463.503 67.185 471.553 67.185C476.733 67.185 480.653 65.435 483.313 61.935L494.233 68.235C489.053 75.725 481.423 79.47 471.343 79.47C462.663 79.47 455.698 76.845 450.448 71.595C445.198 66.345 442.573 59.73 442.573 51.75C442.573 43.84 445.163 37.26 450.343 32.01C455.523 26.69 462.173 24.03 470.293 24.03C477.993 24.03 484.328 26.69 489.298 32.01C494.338 37.33 496.858 43.91 496.858 51.75C496.858 53.5 496.683 55.355 496.333 57.315H456.748ZM456.538 46.815H483.313C482.543 43.245 480.933 40.585 478.483 38.835C476.103 37.085 473.373 36.21 470.293 36.21C466.653 36.21 463.643 37.155 461.263 39.045C458.883 40.865 457.308 43.455 456.538 46.815ZM536.134 4.49999C546.214 4.49999 554.614 8.035 561.334 15.105C568.124 22.175 571.519 30.89 571.519 41.25C571.519 51.61 568.124 60.325 561.334 67.395C554.614 74.465 546.214 78 536.134 78H506.944V4.49999H536.134ZM536.134 64.14C542.504 64.14 547.684 62.04 551.674 57.84C555.664 53.57 557.659 48.04 557.659 41.25C557.659 34.46 555.664 28.965 551.674 24.765C547.684 20.495 542.504 18.36 536.134 18.36H521.434V64.14H536.134ZM626.213 39.885C632.653 43.595 635.873 49.195 635.873 56.685C635.873 62.915 633.668 68.025 629.258 72.015C624.848 76.005 619.423 78 612.983 78H581.798V4.49999H610.778C617.078 4.49999 622.363 6.45999 626.633 10.38C630.973 14.23 633.143 19.165 633.143 25.185C633.143 31.275 630.833 36.175 626.213 39.885ZM610.778 18.045H596.288V34.215H610.778C613.018 34.215 614.873 33.445 616.343 31.905C617.883 30.365 618.653 28.44 618.653 26.13C618.653 23.82 617.918 21.895 616.448 20.355C614.978 18.815 613.088 18.045 610.778 18.045ZM612.983 64.455C615.433 64.455 617.463 63.65 619.073 62.04C620.683 60.36 621.488 58.26 621.488 55.74C621.488 53.29 620.683 51.26 619.073 49.65C617.463 47.97 615.433 47.13 612.983 47.13H596.288V64.455H612.983Z",
       :fill "#1E1B4B"}]
     [:path
      {:fill-rule "evenodd",
       :clip-rule "evenodd",
       :d "M69.209 4H49.4775V78.3125H69.209V4Z",
       :fill "#4F46E5"}]
     [:path
      {:fill-rule "evenodd",
       :clip-rule "evenodd",
       :d "M46.4707 4H26.7393V78.3125H46.4707V4Z",
       :fill "#4F46E5"}]
     [:path
      {:fill-rule "evenodd",
       :clip-rule "evenodd",
       :d "M23.7315 4H4V78.3125H23.7315V4Z",
       :fill "#4F46E5"}]
     [:path
      {:fill-rule "evenodd",
       :clip-rule "evenodd",
       :d
       "M72.2168 4.0061V52.1171C72.2168 59.0833 74.9821 65.7023 79.9045 70.626C84.8269 75.5484 91.4472 78.3137 98.4134 78.3137H109.947V58.5822H98.4134C96.6958 58.5822 95.0669 57.8866 93.8554 56.6751C92.6439 55.4635 91.9483 53.8346 91.9483 52.1171V24.203C91.9483 13.2501 83.1118 4.2585 72.2168 4.00733V4.0061Z",
       :fill "#4F46E5"}]]]))

(defn rebrand-logo []
  (clerk/image (io/file  "resources/img/rebrand.png")))

(defn height-slider [& {:keys [id]}]
  (clerk/with-viewer
    {:render-fn
     '(fn [id _]
        (let [!height (nextjournal.clerk.render.hooks/use-state 400)]
          [:div [:input {:type "range"
                         :min "200" :max "1000" :default-value (str @!height)
                         :on-change #(let [height (-> % .-target .-value)
                                           target (-> js/document (.getElementById id))]
                                       #_(js/console.log (clj->js arg1) (clj->js arg2) "id" target height)
                                       (set! (-> target .-height) height))}]]))}
    id))

(defn iframe-figma-height-slider []
  (height-slider :id "architecture-embed"))

(defn iframe-figma-architecture []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe#architecture-embed
    {:src "https://embed.figma.com/design/zCDYasApKCYMWH7yh3eYLy/Untitled?node-id=0-1&node-type=canvas&t=GJbnd6q0leqzwDc5-0&embed-host=dont-stop-retrievin-slides&footer=false"
     :height "300px" :width "100%"}]))

(defn iframe-homepage []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/homepage.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

(defn iframe-quickstart []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/quickstart.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ### What is MusicBrainz?
(defn iframe-changelog []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/changelog.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ### What is MusicBrainz?
(defn iframe-create-index []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/create-index.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ### What is MusicBrainz?
(defn iframe-install []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/install.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ### What is MusicBrainz?
(defn iframe-range []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/range.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ### A Token of Appreciation
(defn iframe-tokenizers []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/tokenizers.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ## Why We Chose AGPL

;; Only license meeting all criteria: familiar, future-proof, community-driven.
;; 
;; AGPL enables our dual open-source and commercial licensing.
(defn iframe-agpl []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/agpl.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

;; ---
;; ## A Case Study
(defn iframe-alibaba []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/alibaba.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))

(defn iframe-musicbrainz []
  (clerk/html
   {:nextjournal.clerk/width :full}
   [:iframe {:srcdoc (slurp "resources/html/about-musicbrainz.html")
             :width "100%"
             :height "600px"
             :frameBorder "0"
             :allowFullScreen true}]))
