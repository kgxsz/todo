import React, { Component } from "react";
import { connect } from "react-redux";
import {
  sortByDescAddedAt,
  toggleItemChecked,
  deleteItem,
  updateInputValue,
  addItemToItemList
} from "./actions";
import { validateInputValue } from "./utils";
import checkboxSprite from "./checkbox-sprite.svg";
import trashIcon from "./trash-icon.svg";
import smileyIcon from "./smiley-icon.svg";
import sortIcon from "./sort-icon.svg";
import "./App.css";

class ItemAdder extends Component {
  render() {
    let buttonClassName = this.props.validInputValue
      ? "ItemAdder__button"
      : "ItemAdder__button ItemAdder__button--disabled";

    return (
      <form
        onSubmit={e => {
          if (this.props.validInputValue) {
            let addedAt = Date.now();
            this.props.addItemToItemList(addedAt);
          }
          e.preventDefault();
        }}
        className="ItemAdder"
      >
        <input
          className="ItemAdder__input"
          type="text"
          value={this.props.inputValue}
          placeholder="add an item here"
          onChange={e => {
            this.props.updateInputValue(e.target.value);
            e.preventDefault();
          }}
        />
        <input
          className={buttonClassName}
          type="submit"
          value="add"
          disabled={!this.props.validInputValue}
        />
      </form>
    );
  }
}

const ItemAdderContainer = connect(
  state => {
    return {
      validInputValue: validateInputValue(state.inputValue),
      inputValue: state.inputValue
    };
  },
  dispatch => {
    return {
      updateInputValue: inputValue => {
        dispatch(updateInputValue(inputValue));
      },
      addItemToItemList: addedAt => {
        dispatch(addItemToItemList(addedAt));
      }
    };
  }
)(ItemAdder);

class Item extends Component {
  render() {
    let checkboxSpriteClassName = this.props.checked
      ? "Item__checkbox__sprite Item__checkbox__sprite--shifted"
      : "Item__checkbox__sprite";

    return (
      <li className="Item">
        <button
          className="Item__checkbox"
          onClick={this.props.toggleItemChecked}
        >
          <img
            className={checkboxSpriteClassName}
            src={checkboxSprite}
            alt="checkbox"
          />
        </button>

        <div className="Item__value">{this.props.value}</div>

        <button className="Item__trash" onClick={this.props.deleteItem}>
          <img src={trashIcon} alt="trash" />
        </button>
      </li>
    );
  }
}

class ItemList extends Component {
  render() {
    return (
      <div className="ItemList">
        {!this.props.emptyList && (
          <div className="ItemList__options">
            <div className="ItemList__options__divider" />
            <button
              className="ItemList__options__sort"
              onClick={this.props.toggleSortByDescAddedAt}
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
        {!this.props.emptyList && (
          <ul>
            {this.props.items.map(item => (
              <Item
                key={item.addedAt.toString()}
                {...item}
                toggleItemChecked={() => {
                  this.props.toggleItemChecked(item.addedAt);
                }}
                deleteItem={() => {
                  this.props.deleteItem(item.addedAt);
                }}
              />
            ))}
          </ul>
        )}
      </div>
    );
  }
}

const ItemListContainer = connect(
  state => {
    return {
      emptyList: state.itemList.length < 1,
      items: state.itemList.map(addedAt => state.itemsByAddedAt[addedAt])
    };
  },
  dispatch => {
    return {
      toggleSortByDescAddedAt: value => {
        dispatch(sortByDescAddedAt());
      },
      toggleItemChecked: addedAt => {
        dispatch(toggleItemChecked(addedAt));
      },
      deleteItem: addedAt => {
        dispatch(deleteItem(addedAt));
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
          <ItemAdderContainer />
          <div className="App__body__divider" />
          <ItemListContainer />
        </div>
      </div>
    );
  }
}

export default App;
