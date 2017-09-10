import React, { Component } from "react";
import checkboxSprite from "./checkbox-sprite.svg";
import trashIcon from "./trash-icon.svg";
import "./App.css";

class ItemAdder extends Component {
  constructor() {
    super();
    this.state = {
      inputValue: ""
    };
    this.validateInputValue = this.validateInputValue.bind(this);
    this.buttonClassName = this.buttonClassName.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  validateInputValue() {
    let { inputValue } = this.state;
    let { validateItemValue } = this.props;
    return validateItemValue(inputValue);
  }

  buttonClassName() {
    let c = "ItemAdder__button";
    if (!this.validateInputValue()) {
      c += " ItemAdder__button--disabled";
    }
    return c;
  }

  handleChange(e) {
    this.setState({ inputValue: e.target.value });
  }

  handleSubmit(e) {
    let { inputValue } = this.state;
    let { addItemToItemList } = this.props;
    if (this.validateInputValue()) {
      this.setState({ inputValue: "" });
      addItemToItemList(inputValue);
    } else {
      console.error("Attempted to handle submit with invalid inputValue");
    }
    e.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit} className="ItemAdder">
        <input
          className="ItemAdder__input"
          type="text"
          value={this.state.inputValue}
          placeholder="add an item here"
          onChange={this.handleChange}
        />
        <input
          className={this.buttonClassName()}
          type="submit"
          value="add"
          disabled={!this.validateInputValue()}
        />
      </form>
    );
  }
}

class Item extends Component {
  constructor() {
    super();
    this.spriteClassName = this.spriteClassName.bind(this);
  }

  spriteClassName() {
    let c = "Item__checkbox__sprite";
    if (this.props.item.checked) {
      c += " Item__checkbox__sprite--shifted";
    }
    return c;
  }

  render() {
    return (
      <li className="Item">
        <div
          className="Item__checkbox"
          onClick={() => {
            this.props.toggleItemChecked(this.props.item.addedAt);
          }}
        >
          <img
            className={this.spriteClassName()}
            src={checkboxSprite}
            alt="checkbox"
          />
        </div>

        <div className="Item__value">
          {this.props.item.value}
        </div>

        <div
          className="Item__trash"
          onClick={() => {
            this.props.deleteItem(this.props.item.addedAt);
          }}
        >
          <img src={trashIcon} alt="trash" />
        </div>
      </li>
    );
  }
}

class ItemList extends Component {
  render() {
    return (
      <div className="ItemList">
        <ul>
          {this.props.itemList.map(key =>
            <Item
              key={key.toString()}
              item={this.props.itemsByAddedAt[key]}
              toggleItemChecked={this.props.toggleItemChecked}
              deleteItem={this.props.deleteItem}
            />
          )}
        </ul>
      </div>
    );
  }
}

class App extends Component {
  constructor() {
    super();
    this.state = {
      itemList: [],
      itemsByAddedAt: {}
    };
    this.validateItemValue = this.validateItemValue.bind(this);
    this.coerceInputValueToItemValue = this.coerceInputValueToItemValue.bind(
      this
    );
    this.addItemToItemList = this.addItemToItemList.bind(this);
    this.toggleItemChecked = this.toggleItemChecked.bind(this);
    this.deleteItem = this.deleteItem.bind(this);
  }

  validateItemValue(itemValue) {
    let ItemValueLength = itemValue.trim().length;
    return itemValue && ItemValueLength > 0 && ItemValueLength < 256;
  }

  coerceInputValueToItemValue(inputValue) {
    return inputValue.trim();
  }

  addItemToItemList(inputValue) {
    let value = this.coerceInputValueToItemValue(inputValue);
    let addedAt = Date.now();
    let item = { addedAt: addedAt, value: value, checked: false };
    let itemsByAddedAt = this.state.itemsByAddedAt;
    let itemList = this.state.itemList;
    itemList.push(addedAt);
    itemsByAddedAt[addedAt] = item;
    this.setState({ itemList: itemList, itemsByAddedAt: itemsByAddedAt });
  }

  toggleItemChecked(addedAt) {
    let itemsByAddedAt = this.state.itemsByAddedAt;
    itemsByAddedAt[addedAt].checked = !itemsByAddedAt[addedAt].checked;
    this.setState({ itemsByAddedAt: itemsByAddedAt });
  }

  deleteItem(addedAt) {
    let itemsByAddedAt = this.state.itemsByAddedAt;
    let itemList = this.state.itemList;
    delete itemsByAddedAt[addedAt];

    let index = itemList.indexOf(addedAt);
    if (index > -1) {
      itemList.splice(index, 1);
    }
    this.setState({ itemList: itemList, itemsByAddedAt: itemsByAddedAt });
  }

  render() {
    return (
      <div className="App">
        <div className="App__header">
          <span className="App__header__title">Todo List</span>
          <span className="App__header__subtitle">Developed with React</span>
        </div>
        <div className="App__body">
          <ItemAdder
            validateItemValue={this.validateItemValue}
            addItemToItemList={this.addItemToItemList}
          />
          <ItemList
            itemList={this.state.itemList}
            itemsByAddedAt={this.state.itemsByAddedAt}
            toggleItemChecked={this.toggleItemChecked}
            deleteItem={this.deleteItem}
          />
        </div>
      </div>
    );
  }
}

export default App;
