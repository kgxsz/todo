import { validateInputValue } from "./utils";

const initialState = {
  sortByDescAddedAt: false,
  itemsByAddedAt: {},
  itemList: [],
  inputValue: ""
};

const appReducer = (state = initialState, action) => {
  switch (action.type) {
    case "TOGGLE_SORT_BY_DESC_ADDED_AT":
      return state.sortByDescAddedAt ? false : true;

    case "TOGGLE_ITEM_CHECKED":
      return {
        ...state,
        itemsByAddedAt: {
          ...state.itemsByAddedAt,
          [action.addedAt]: {
            ...state.itemsByAddedAt[action.addedAt],
            checked: !state.itemsByAddedAt[action.addedAt].checked
          }
        }
      };

    case "DELETE_ITEM":
      let itemsByAddedAt = Object.assign({}, state.itemsByAddedAt);
      let index = state.itemList.indexOf(action.addedAt);
      delete itemsByAddedAt[action.addedAt];
      return {
        ...state,
        itemList: [
          ...state.itemList.slice(0, index),
          ...state.itemList.slice(index + 1)
        ],
        itemsByAddedAt: itemsByAddedAt
      };

    case "UPDATE_INPUT_VALUE":
      return {
        ...state,
        inputValue: action.inputValue
      };

    case "ADD_ITEM_TO_ITEM_LIST":
      if (validateInputValue(state.inputValue)) {
        let item = {
          addedAt: action.addedAt,
          value: state.inputValue,
          checked: false
        };
        return {
          ...state,
          inputValue: "",
          itemsByAddedAt: {
            ...state.itemsByAddedAt,
            [item.addedAt]: item
          },
          itemList: [item.addedAt, ...state.itemList.slice(0)]
        };
      } else {
        return state;
      }

    default:
      return state;
  }
};

export default appReducer;
