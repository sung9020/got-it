package com.snow.gotit.base.response

import java.time.LocalDateTime

sealed class ResultResponse<out T>(){
    data class Success<out T>(val value: T) : ResultResponse<T>()
    data class Error(
        val errorMessage: String? = "",
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val data: Map<String, Any>? = null,
    ) : ResultResponse<Nothing>()
}