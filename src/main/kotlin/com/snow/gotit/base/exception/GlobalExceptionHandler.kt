package com.snow.gotit.base.exception

import com.snow.gotit.base.response.ResultResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

   @ExceptionHandler(GotItException::class)
   fun handleGotItHomeworkException(ex: GotItException): ResponseEntity<ResultResponse<Nothing>>{

       val response = ResultResponse.Error(
           errorMessage = ex.message,
           data = ex.data,
       )

       return ResponseEntity(response, ex.status)
   }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ResultResponse<Nothing>>{
        val httpStatus = HttpStatus.BAD_REQUEST
        val response = ResultResponse.Error(
            errorMessage = ex.message ?: "잘못된 요청입니다."
        )

        return ResponseEntity(response, httpStatus)
    }
}