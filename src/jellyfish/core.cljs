(ns jellyfish.core
    (:require [sablono.core :as sab]
              [rum.core :as rum]))

(enable-console-print!)

(defn push
  [{:keys [item to]}]
  (conj to item))

(def collection [{:title "A" :color "red" :url "http://google.com" :text "text here"}
                 {:title "B" :color "orange" :url "http://dribbble.com" :text "text here"}
                 {:title "C" :color "green" :url "http://github.com" :text "text here"}
                 {:title "D" :color "red" :url "http://dribbble.com" :text "text here"}
                 {:title "E" :color "orange" :url "http://dribbble.com" :text "text here"}
                 {:title "F" :color "green" :url "http://dribbble.com" :text "text here"}])

(rum/defcs card-form
  < (rum/local {:title ""})
  [state app-state]
  (let [local-state (:rum/local state)
        title (:title @local-state)]
    [:div.form-wrapper
     [:input { :type "text"
               :value title
               :placeholder "add a card"
               :on-change (fn [e]
                           (swap! local-state assoc :title (.. e -target -value))
                           (js/console.log (.. e -target -value)))}]
     [:button {:type "button" :on-click (fn [event]
                                          (swap! local-state assoc :title "")
                                          (swap! app-state assoc :collection (push {:item {:title title
                                                                                           :color (rand-nth ["red" "orange" "green" "blue"])
                                                                                           :url "http://jira.com"
                                                                                           :text "text here"}
                                                                                    :to (:collection @app-state)}))
                                          (js/console.log title))} "save"]]))

(rum/defc card-item [data]
  [:div.card
   [:div.card-wrapper
    [:div {:class ["card-header", (data :color)]}
     [:label (data :title)]]
    [:div.card-body
     [:p (data :text)]
     [:a {:href (data :url)} "Learn more"]]]])

(rum/defc card-list [list]
  [:div.cards-container
   (map-indexed (fn [index card]
                  (rum/with-key (card-item card) index))
                list)])

(rum/defcs app
  < (rum/local {:is-open? false :collection collection})
  [state]
  (let [app-state (:rum/local state)
        is-open? (:is-open? @app-state)
        local-collection (:collection @app-state)]
   [:div
    (card-form app-state)
    (card-list local-collection)
    (str @app-state)]))


  (rum/mount (app) (.getElementById js/document "app"))

