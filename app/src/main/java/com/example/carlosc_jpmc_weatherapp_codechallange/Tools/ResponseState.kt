package com.example.carlosc_jpmc_weatherapp_codechallange.Tools

import retrofit2.Response

sealed class ResponseState<out T> {

    object LOADING: ResponseState<Nothing>()
    data class SUCCESS<T> (val response: T): ResponseState<T>()
    data class ERROR(val error: Exception): ResponseState<Nothing>()
}