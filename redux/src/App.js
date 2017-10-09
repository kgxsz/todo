import React, { Component } from "react";
import { connect } from "react-redux";
import { sortByDescAddedAt } from "./actions";
import smileyIcon from "./smiley-icon.svg";
import sortIcon from "./sort-icon.svg";
import "./App.css";

class ItemList extends Component {
  render() {
    return (
      <div className="ItemList">
        {!this.props.emptyList && (
          <div className="ItemList__options">
            <div className="ItemList__options__divider" />
            <button
              className="ItemList__options__sort"
              onClick={this.props.onToggleSortByDescAddedAtClick}
            >
              <img src={sortIcon} alt="sort" />
            </button>
          </div>
        )}
        {this.props.emptyList && (
          <div className="ItemList__notice">
            <img
              className="ItemList__notice__icon"
              src={smileyIcon}
              alt="smiley"
            />
            There are no items
          </div>
        )}
        {/* {!emptyList && (
          <ul>
            {this.props.itemList.map(key => (
              <Item
                key={key.toString()}
                item={this.props.itemsByAddedAt[key]}
                toggleItemChecked={this.props.toggleItemChecked}
                deleteItem={this.props.deleteItem}
              />
            ))}
          </ul>
        )} */}
      </div>
    );
  }
}

const ItemListContainer = connect(
  state => {
    return {
      emptyList: true, //state.itemList.length < 1;
      sortByDescAddedAt: state.sortByDescAddedAt
    };
  },
  dispatch => {
    return {
      onToggleSortByDescAddedAtClick: () => {
        dispatch(sortByDescAddedAt());
      }
    };
  }
)(ItemList);

class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="App__header">
          <span className="App__header__title">Todo List</span>
          <span className="App__header__subtitle">Developed with Redux</span>
        </div>
        <div className="App__body">
          <div className="App__body__divider" />
          {/* <ItemAdder
            validateItemValue={this.validateItemValue}
            addItemToItemList={this.addItemToItemList}
          /> */}
          <div className="App__body__divider" />
          <ItemListContainer />
        </div>
      </div>
    );
  }
}

export default App;
