package com.mad.assignment4.network.retrofit.eventWrapper

sealed class ResponseState<out T>(
    val data: T? = null,
    val message: String? = null
) {
    object Initial : ResponseState<Nothing>()

    object Loading : ResponseState<Nothing>()

    class Success<T>(response: T) : ResponseState<T>(data = response)

    class Failure<T>(errorMessage: String) : ResponseState<T>(message = errorMessage)
}
