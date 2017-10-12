export const sortByDescAddedAt = () => {
  return {
    type: "TOGGLE_SORT_BY_DESC_ADDED_AT"
  };
};

export const toggleItemChecked = addedAt => {
  return {
    type: "TOGGLE_ITEM_CHECKED",
    addedAt: addedAt
  };
};

export const deleteItem = addedAt => {
  return {
    type: "DELETE_ITEM",
    addedAt: addedAt
  };
};

export const updateInputValue = inputValue => {
  return {
    type: "UPDATE_INPUT_VALUE",
    inputValue: inputValue
  };
};

export const addItemToItemList = addedAt => {
  return {
    type: "ADD_ITEM_TO_ITEM_LIST",
    addedAt: addedAt
  };
};
