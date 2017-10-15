(ns re-frame-todo.views
  (:require [re-frame.core :as re-frame]
            [re-frame-todo.schema :as schema]
            [cljs.spec.alpha :as spec]))

(defn item-adder []
  (let [!input-value (re-frame/subscribe [:input-value])
        add-item-to-item-list (fn [e]
                                (let [added-at (.now js/Date)]
                                  (re-frame/dispatch [:add-item-to-item-list added-at]))
                                (.preventDefault e))
        update-input-value (fn [e]
                             (let [input-value (-> e .-target .-value)]
                               (re-frame/dispatch [:update-input-value input-value])))]
    (fn []
      (let [valid-input-value? (spec/valid? ::schema/text @!input-value)]
        [:form.item-adder
         {:on-submit add-item-to-item-list}
         [:input.item-adder__input
          {:type :text
           :value @!input-value
           :placeholder "add an item here"
           :on-change update-input-value}]
         [:input.item-adder__button
          {:class (when-not valid-input-value? "item-adder__button--disabled")
           :type :submit
           :value "add"
           :disabled (not valid-input-value?)}]]))))

(defn item [added-at]
  (let [!item (re-frame/subscribe [:item added-at])
        toggle-item-checked? (fn []
                               (re-frame/dispatch [:toggle-item-checked? added-at]))
        delete-item-from-item-list (fn []
                                     (re-frame/dispatch [:delete-item-from-item-list added-at]))]
    (fn []
      (let [{:keys [text checked?]} @!item]
        [:li.item
         [:button.item__checkbox
          {:on-click toggle-item-checked?}
          [:img.item__checkbox__sprite
           {:class (when checked? "item__checkbox__sprite--shifted")
            :alt :checkbox
            :src "images/checkbox-sprite.svg"}]]
         [:div.item__text
          text]
         [:button.item__trash
          {:on-click delete-item-from-item-list}
          [:img
           {:alt :trash
            :src "images/trash-icon.svg"}]]]))))

(defn item-list []
  (let [!item-list (re-frame/subscribe [:item-list])
        toggle-sort-items-by-desc-added-at? (fn []
                                              (re-frame/dispatch [:toggle-sort-item-by-desc-added-at?]))]
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
           {:on-click toggle-sort-items-by-desc-added-at?}
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

