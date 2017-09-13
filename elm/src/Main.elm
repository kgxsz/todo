module Main exposing (..)

import Dict exposing (Dict)
import Html exposing (Html, div, form, img, input, span, text)
import Html.Attributes exposing (class, placeholder, src, type_, value)
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
      , itemList = []
      , itemsByAddedAt = Dict.empty
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
    div [ class "ItemList" ]
        [ div [ class "ItemList__notice" ]
            [ img [ class "ItemList__notice__icon", src model.flags.smileyIconPath ] [] ]
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
