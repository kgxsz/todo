module Main exposing (..)

import Dict exposing (Dict)
import Html exposing (Html, button, div, form, img, input, li, span, text, ul)
import Html.Attributes exposing (alt, class, disabled, placeholder, src, type_, value)
import Html.Events exposing (onInput, onSubmit)
import Task
import Time exposing (Time)


---- MODEL ----


type alias Flags =
    { smileyIconPath : String
    , trashIconPath : String
    , sortIconPath : String
    , checkboxSpritePath : String
    }


type alias Item =
    { addedAt : Time
    , value : String
    , checked : Bool
    }


type alias Model =
    { flags : Flags
    , inputValue : String
    , itemList : List Time
    , itemsByAddedAt : Dict Time Item
    , sortByDescAddedAt : Bool
    }


init : Flags -> ( Model, Cmd Msg )
init flags =
    ( { flags = flags
      , inputValue = ""
      , itemList = [ 0 ]
      , itemsByAddedAt = Dict.empty
      , sortByDescAddedAt = True
      }
    , Cmd.none
    )



---- UPDATE ----


type Msg
    = UpdateInputValue String
    | SubmitInputValue
    | AddItem Time


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        UpdateInputValue inputValue ->
            ( { model | inputValue = inputValue }, Cmd.none )

        SubmitInputValue ->
            ( model, Task.perform AddItem Time.now )

        AddItem addedAt ->
            let
                updatedModel =
                    { model
                        | inputValue = ""
                        , itemList =
                            List.sortWith
                                (\x y ->
                                    case compare x y of
                                        LT ->
                                            if model.sortByDescAddedAt then
                                                GT
                                            else
                                                LT

                                        EQ ->
                                            EQ

                                        GT ->
                                            if model.sortByDescAddedAt then
                                                LT
                                            else
                                                GT
                                )
                                (addedAt :: model.itemList)
                        , itemsByAddedAt =
                            Dict.insert addedAt
                                { addedAt = addedAt
                                , value = model.inputValue
                                , checked = False
                                }
                                model.itemsByAddedAt
                    }
            in
            ( updatedModel, Cmd.none )



---- VIEW ----


view : Model -> Html Msg
view model =
    div [ class "App" ]
        [ div [ class "App__header" ]
            [ span [ class "App__header__title" ] [ text "Todo List" ]
            , span [ class "App__header__subtitle" ] [ text "Developed with Elm" ]
            ]
        , div [ class "App__body" ]
            [ div [ class "App__body__divider" ] []
            , itemAdder model
            , div [ class "App__body__divider" ] []
            , itemList model
            ]
        ]


validInputValue : String -> Bool
validInputValue value =
    (value |> String.trim |> String.isEmpty |> not)
        && ((value |> String.trim |> String.length) < 256)


itemAdder : Model -> Html Msg
itemAdder model =
    let
        buttonClass =
            if validInputValue model.inputValue then
                "ItemAdder__button"
            else
                "ItemAdder__button ItemAdder__button--disabled"
    in
    form [ class "ItemAdder", onSubmit SubmitInputValue ]
        [ input
            [ class "ItemAdder__input"
            , type_ "text"
            , placeholder "add an item here"
            , value model.inputValue
            , onInput UpdateInputValue
            ]
            []
        , input
            [ class buttonClass
            , type_ "submit"
            , value "add"
            , disabled (model.inputValue |> validInputValue |> not)
            ]
            []
        ]


itemList : Model -> Html Msg
itemList model =
    let
        items =
            List.filterMap
                (\x -> Dict.get x model.itemsByAddedAt)
                model.itemList
    in
    div [ class "ItemList" ]
        (if List.isEmpty items then
            [ div [ class "ItemList__notice" ]
                [ img
                    [ class "ItemList__notice__icon"
                    , src model.flags.smileyIconPath
                    , alt "smiley"
                    ]
                    []
                , text "There are no items"
                ]
            ]
         else
            [ div [ class "ItemList__options" ]
                [ div [ class "ItemList__options__divider" ] []
                , button [ class "ItemList__options__sort" ]
                    [ img
                        [ src model.flags.sortIconPath
                        , alt "sort"
                        ]
                        []
                    ]
                ]
            , ul []
                (List.map
                    (\x ->
                        item x
                            { checkboxSpritePath = model.flags.checkboxSpritePath
                            , trashIconPath = model.flags.trashIconPath
                            }
                    )
                    items
                )
            ]
        )


type alias ItemAttr =
    { checkboxSpritePath : String, trashIconPath : String }


item : Item -> ItemAttr -> Html Msg
item item { checkboxSpritePath, trashIconPath } =
    let
        spriteClass =
            if not item.checked then
                "Item__checkbox__sprite"
            else
                "Item__checkbox__sprite Item__checkbox__sprite--shifted"
    in
    li [ class "Item" ]
        [ button [ class "Item__checkbox" ]
            [ img
                [ class spriteClass
                , src checkboxSpritePath
                , alt "checkbox"
                ]
                []
            ]
        , div [ class "Item__value" ]
            [ text item.value ]
        , button [ class "Item__trash" ]
            [ img
                [ src trashIconPath
                , alt "trash"
                ]
                []
            ]
        ]



---- PROGRAM ----


main : Program Flags Model Msg
main =
    Html.programWithFlags
        { view = view
        , init = init
        , update = update
        , subscriptions = always Sub.none
        }
