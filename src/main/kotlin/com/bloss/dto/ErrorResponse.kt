package com.bloss.dto

data class ErrorResponse(
    val status: Status,
    val code: Int,
    val message: String?
) {
    companion object {
        fun badRequest(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.BAD_REQUEST,
                message = e.message,
                code = code
            )
        }

        fun unauthorized(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.UNAUTHORIZED,
                message = e.message,
                code = code
            )
        }

        fun validationException(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.VALIDATION_EXCEPTION,
                message = e.message,
                code = code
            )
        }

        fun wrongCredentials(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.WRONG_CREDENTIALS,
                message = e.message,
                code = code
            )
        }

        fun accessDenied(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.ACCESS_DENIED,
                message = e.message,
                code = code
            )
        }

        fun exception(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.EXCEPTION,
                message = e.message,
                code = code
            )
        }

        fun notFound(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.NOT_FOUND,
                message = e.message,
                code = code
            )
        }

        fun duplicateEntity(e: Exception, code: Int): ErrorResponse {
            return ErrorResponse(
                status = Status.DUPLICATE_ENTITY,
                message = e.message,
                code = code
            )
        }
    }

    enum class Status {
        BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED, NOT_FOUND, DUPLICATE_ENTITY
    }
}