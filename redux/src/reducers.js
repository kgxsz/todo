import { combineReducers } from "redux";

const sortByDescAddedAt = (state = true, action) => {
  switch (action.type) {
    case "TOGGLE_SORT_BY_DESC_ADDED_AT":
      return state ? false : true;
    default:
      return state;
  }
};

let initialItem = {
  addedAt: Date.now(),
  value: "do something!",
  checked: false
};

let initialItemsByAddedAt = {};
initialItemsByAddedAt[initialItem.addedAt] = initialItem;

let initialItemList = [];
initialItemList.push(initialItem.addedAt);

const itemsByAddedAt = (state = initialItemsByAddedAt, action) => {
  switch (action.type) {
    case "TOGGLE_ITEM_CHECKED":
      return {
        ...state,
        [action.addedAt]: {
          ...state[action.addedAt],
          checked: !state[action.addedAt].checked
        }
      };
    case "DELETE_ITEM":
      let itemsByAddedAt = Object.assign({}, state);
      delete itemsByAddedAt[action.addedAt];
      return itemsByAddedAt;
    default:
      return state;
  }
};

const itemList = (state = initialItemList, action) => {
  switch (action.type) {
    case "DELETE_ITEM":
      let index = state.indexOf(action.addedAt);
      return [...state.slice(0, index), ...state.slice(index + 1)];
    default:
      return state;
  }
};

const appReducer = combineReducers({
  sortByDescAddedAt,
  itemsByAddedAt,
  itemList
});

export default appReducer;
