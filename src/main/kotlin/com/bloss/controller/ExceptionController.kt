package com.bloss.controller

import com.bloss.dto.ErrorResponse
import com.bloss.exception.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: Exception): ErrorResponse {
        return ErrorResponse.badRequest(ex, 1)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtAuthenticationException::class)
    fun handleJwtAuthenticationException(ex: Exception): ErrorResponse {
        return ErrorResponse.wrongCredentials(ex, 2)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtResolvationException::class)
    fun handleJwtResolvationException(ex: Exception): ErrorResponse {
        return ErrorResponse.wrongCredentials(ex, 3)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoBadWordsDictionaryException::class)
    fun handleNoBadWordsDictionaryException(ex: Exception): ErrorResponse {
        return ErrorResponse.exception(ex, 4)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongCredentialsException::class)
    fun handleWrongCredentialsException(ex: Exception): ErrorResponse {
        return ErrorResponse.wrongCredentials(ex, 5)
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(LoginException::class)
//    fun handleLoginException(ex: Exception): ErrorResponse {
//        return ErrorResponse.wrongCredentials(ex, 5)
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: Exception): ErrorResponse {
        return ErrorResponse.validationException(ex, 6)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: Exception): ErrorResponse {
        return ErrorResponse.validationException(ex, 7)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExists::class)
    fun handleEntityAlreadyExists(ex: Exception): ErrorResponse {
        return ErrorResponse.validationException(ex, 8)
    }
}