package com.mad.assignmentFive.network.retrofit.eventWrapper



sealed class ResponseState<out T>(
    val data: T? = null,
    val message: String? = null
) {
    object Initial : ResponseState<Nothing>()

    object Loading: ResponseState<Nothing>()

    data class Success<T>(val response: T) : ResponseState<T>(data = response)

    data class Failure<T>(val errorMessage: String): ResponseState<T>(message = errorMessage)
}