package dev.vanderblom.inventorybackenddemo.web.exceptionhandling

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange


@ControllerAdvice
class ExceptionHandlers {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(
        exception: EntityNotFoundException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), HttpStatus.BAD_REQUEST)
    }
}

