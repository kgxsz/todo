import { combineReducers } from "redux";

const sortByDescAddedAt = (state = true, action) => {
  switch (action.type) {
    case "TOGGLE_SORT_BY_DESC_ADDED_AT":
      return state ? false : true;
    default:
      return state;
  }
};

const itemsByAddedAt = (state = {}, action) => {
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
    case "ADD_ITEM_TO_ITEM_LIST":
      return {
        ...state,
        [action.item.addedAt]: action.item
      };
    default:
      return state;
  }
};

const itemList = (state = [], action) => {
  switch (action.type) {
    case "DELETE_ITEM":
      let index = state.indexOf(action.addedAt);
      return [...state.slice(0, index), ...state.slice(index + 1)];
    case "ADD_ITEM_TO_ITEM_LIST":
      return [action.item.addedAt, ...state.slice(0)];
    default:
      return state;
  }
};

const inputValue = (state = "", action) => {
  switch (action.type) {
    case "UPDATE_INPUT_VALUE":
      return action.value;
    case "ADD_ITEM_TO_ITEM_LIST":
      return "";
    default:
      return state;
  }
};

const appReducer = combineReducers({
  sortByDescAddedAt,
  itemsByAddedAt,
  itemList,
  inputValue
});

export default appReducer;
