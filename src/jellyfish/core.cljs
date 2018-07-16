(ns jellyfish.core
    (:require [sablono.core :as sab]))

(enable-console-print!)

(defonce app-state (atom {:text "Bordered type just draws a line between content and actions.: " :likes 0 :is-open? false}))

(defn card [data]
  (sab/html [:div.card
              [:div.card-wrapper
                [:div {:class ["card-header", (data :color)]}
                  [:label (data :title)]]

                [:div.card-body
                  [:p (data :text)]
                  [:a {:href (data :url)} "Learn more"]]]]))

(defn app [data]
  (sab/html
    [:div.cards-container
      (card {:color "red" :title "Red" :url "http://google.com" :text "Bordered type just draws a line between content and actions."})
      (card {:color "orange" :title "Orange" :url "http://google.com" :text "Bordered type just draws a line between content and actions."})
      (card {:color "yellow" :title "Yellow" :url "http://google.com" :text "Bordered type just draws a line between content and actions."})
      (card {:color "green" :title "Green" :url "http://google.com" :text "Bordered type just draws a line between content and actions."})]))


(defn render! []
  (.render js/ReactDOM
           (app app-state)
           (.getElementById js/document "app")))
(add-watch app-state :on-change (fn [_ _ _ _] (render!)))

(render!)