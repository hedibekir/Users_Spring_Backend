package com.expatrio.api.exception

import com.expatrio.api.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException
import java.util.stream.Collectors


@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationsExceptions(
        ex: MethodArgumentNotValidException,
    ): ErrorResponse {
        val errorList = ex
            .bindingResult
            .fieldErrors
            .stream()
            .map { fieldError: FieldError -> fieldError.defaultMessage }
            .collect(Collectors.toList())
        return ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "User Input Data Validation Failed", errorList)
    }

    @ExceptionHandler(SQLException::class)
    fun handleDatabaseExceptions(
        ex: SQLException
    ): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.message, arrayListOf())
    }

}