(ns re-frame-todo.views
  (:require [re-frame.core :as re-frame]))

(defn item-adder []
  (let [!input-value (re-frame/subscribe [:input-value])]
    (fn []
      (let [valid-input-value? false]
        [:form.item-adder
         [:input.item-adder__input
          {:type :text
           :value @!input-value
           :placeholder "add an item here"
           :on-change (fn [])}]
         [:input.item-adder__button
          {:class (when-not valid-input-value? "item-adder__button--disabled")
           :type :submit
           :value "add"
           :disabled (not valid-input-value?)}]]))))

(defn item [added-at]
  (let [!item (re-frame/subscribe [:item added-at])]
    (fn []
      (let [{:keys [text checked]} @!item]
        [:li.item
         [:button.item__checkbox
          [:img.item__checkbox__sprite
           {:class (when checked "item__checkbox__sprite--shifted")
            :alt :checkbox
            :src "images/checkbox-sprite.svg"}]]
         [:div.item__text
          text]
         [:button.item__trash
          [:img
           {:alt :trash
            :src "images/trash-icon.svg"}]]]))))

(defn item-list []
  (let [!item-list (re-frame/subscribe [:item-list])]
    (fn []
      (if (empty? @!item-list)
        [:div.item-list
         [:div.item-list__notice
          [:img.item-list__notice__icon
           {:alt :smiley
            :src "images/smiley-icon.svg"}]
          "There are no items"]]

        [:div.item-list
         [:div.item-list__options
          [:div.item-list__options__divider]
          [:button.item-list__options__sort
           [:img
            {:alt :sort
             :src "images/sort-icon.svg"}]]]
         [:ul
          (doall
           (for [added-at @!item-list]
             ^{:key added-at} [item added-at]))]]))))

(defn app []
  [:div.app
   [:div.app__header
    [:div.app__header__title
     "Todo List"]
    [:div.app__header__subtitle
     "Developed with Re-Frame"]]
   [:div.app__body
    [:div.app__body__divider]
    [item-adder]
    [:div.app__body__divider]
    [item-list]]])

