import "./normalize.css";
import "./main.css";
import { Main } from "./Main.elm";
import smileyIconPath from "./smiley-icon.svg";
import trashIconPath from "./trash-icon.svg";
import sortIconPath from "./sort-icon.svg";
import checkboxSpritePath from "./checkbox-sprite.svg";

Main.embed(document.getElementById("root"), {
  smileyIconPath: smileyIconPath,
  trashIconPath: trashIconPath,
  sortIconPath: sortIconPath,
  checkboxSpritePath: checkboxSpritePath
});
