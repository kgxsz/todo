import { combineReducers } from "redux";

const sortByDescAddedAt = (state = true, action) => {
  switch (action.type) {
    case "TOGGLE_SORT_BY_DESC_ADDED_AT":
      return state ? false : true;
    default:
      return state;
  }
};

const appReducer = combineReducers({
  sortByDescAddedAt
});

export default appReducer;
