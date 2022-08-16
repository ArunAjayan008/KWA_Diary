package com.polymorfuz.kwadiary.beans

class Enums {
    enum class LoginState {
        DEFAULT, INIT, VERIFIED, SUCCESS, FAILED, LOGOUT
    }

    enum class PageState {
        LOADING, FAILED, SUCCESS, NONE, ERROR, EMPTY, DEFAULT, OVERLAY
    }

    enum class ErrorType {
        SERVER_ERROR, PARSE_ERROR, NO_INTERNET, SERVER_TIMEOUT, NOT_AUTHORISED,NONE
    }
}