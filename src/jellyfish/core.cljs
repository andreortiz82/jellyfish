(ns jellyfish.core
    (:require [sablono.core :as sab]))

(enable-console-print!)

(defonce app-state (atom {:text "How many likes: " :likes 0 :is-open? false}))

(defn app [data]
  (sab/html
    [:div
     "HELLO"]))

(defn render! []
  (.render js/ReactDOM
           (app app-state)
           (.getElementById js/document "app")))
(add-watch app-state :on-change (fn [_ _ _ _] (render!)))

(render!)