module Main exposing (..)

import Dict exposing (Dict)
import Html exposing (Html, button, div, form, img, input, li, span, text, ul)
import Html.Attributes exposing (alt, class, placeholder, src, type_, value)
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
      , itemsByAddedAt = Dict.singleton 0 { addedAt = 0, value = "Dummy Item" }
      , sortByDescAddedAt = True
      }
    , Cmd.none
    )



---- UPDATE ----


type Msg
    = NoOp


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    ( model, Cmd.none )



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


itemAdder : Model -> Html Msg
itemAdder model =
    form [ class "ItemAdder" ]
        [ input
            [ class "ItemAdder__input"
            , type_ "text"
            , placeholder "add an item here"
            , value model.inputValue
            ]
            []
        , input
            [ class "ItemAdder__button"
            , type_ "submit"
            , value "add"
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
                    (\item ->
                        li [ class "Item" ]
                            [ button [ class "Item__checkbox" ]
                                [ img
                                    [ class "Item__checkbox__sprite"
                                    , src model.flags.checkboxSpritePath
                                    , alt "checkbox"
                                    ]
                                    []
                                ]
                            , div [ class "Item__value" ]
                                [ text item.value ]
                            , button [ class "Item__trash" ]
                                [ img
                                    [ src model.flags.trashIconPath
                                    , alt "trash"
                                    ]
                                    []
                                ]
                            ]
                    )
                    items
                )
            ]
        )



---- PROGRAM ----


main : Program Flags Model Msg
main =
    Html.programWithFlags
        { view = view
        , init = init
        , update = update
        , subscriptions = always Sub.none
        }
