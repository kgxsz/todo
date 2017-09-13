module Main exposing (..)

import Html exposing (Html, div, img, span, text)
import Html.Attributes exposing (class, src)


---- MODEL ----


type alias Model =
    {}


init : ( Model, Cmd Msg )
init =
    ( {}, Cmd.none )



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
            , itemAdder
            , div [ class "App__body__divider" ] []
            ]
        ]


itemAdder : Html Msg
itemAdder =
    div [ class "ItemAdder" ] [ text "hello" ]



---- PROGRAM ----


main : Program Never Model Msg
main =
    Html.program
        { view = view
        , init = init
        , update = update
        , subscriptions = always Sub.none
        }
