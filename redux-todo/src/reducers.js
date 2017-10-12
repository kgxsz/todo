import { validateInputValue } from "./utils";

const initialState = {
  sortByDescAddedAt: true,
  itemsByAddedAt: {},
  itemList: [],
  inputValue: ""
};

const sortItemList = (itemList, sortByDescAddedAt) => {
  itemList.sort(function(a, b) {
    if (sortByDescAddedAt) {
      return a > b ? -1 : a < b ? 1 : 0;
    } else {
      return a > b ? 1 : a < b ? -1 : 0;
    }
  });
};

const toggleSortByDescAddedAt = (state, action) => {
  let sortByDescAddedAt = state.sortByDescAddedAt ? false : true;
  let itemList = Object.assign([], state.itemList);
  sortItemList(itemList, sortByDescAddedAt);
  return {
    ...state,
    sortByDescAddedAt: sortByDescAddedAt,
    itemList: itemList
  };
};

const toggleItemChecked = (state, action) => {
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
};

const deleteItem = (state, action) => {
  let itemsByAddedAt = Object.assign({}, state.itemsByAddedAt);
  delete itemsByAddedAt[action.addedAt];

  let itemList = Object.assign([], state.itemList);
  let index = itemList.indexOf(action.addedAt);
  if (index > -1) {
    itemList.splice(index, 1);
  }

  return {
    ...state,
    itemList: itemList,
    itemsByAddedAt: itemsByAddedAt
  };
};

const updateInputValue = (state, action) => {
  return {
    ...state,
    inputValue: action.inputValue
  };
};

const addItemToItemList = (state, action) => {
  if (validateInputValue(state.inputValue)) {
    var item = {
      addedAt: action.addedAt,
      value: state.inputValue,
      checked: false
    };

    let itemsByAddedAt = Object.assign({}, state.itemsByAddedAt);
    itemsByAddedAt[item.addedAt] = item;

    let itemList = Object.assign([], state.itemList);
    itemList.push(item.addedAt);
    sortItemList(itemList, state.sortByDescAddedAt);

    return {
      ...state,
      inputValue: "",
      itemsByAddedAt: itemsByAddedAt,
      itemList: itemList
    };
  } else {
    return state;
  }
};

const appReducer = (state = initialState, action) => {
  switch (action.type) {
    case "TOGGLE_SORT_BY_DESC_ADDED_AT":
      return toggleSortByDescAddedAt(state, action);
    case "TOGGLE_ITEM_CHECKED":
      return toggleItemChecked(state, action);
    case "DELETE_ITEM":
      return deleteItem(state, action);
    case "UPDATE_INPUT_VALUE":
      return updateInputValue(state, action);
    case "ADD_ITEM_TO_ITEM_LIST":
      return addItemToItemList(state, action);
    default:
      return state;
  }
};

export default appReducer;
