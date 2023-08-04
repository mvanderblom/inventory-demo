package dev.vanderblom.inventorybackenddemo.web

import dev.vanderblom.inventorybackenddemo.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange


@ControllerAdvice
class ExceptionHandling {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(exception: ResourceNotFoundException, exchange: ServerWebExchange): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), HttpStatus.NOT_FOUND)
    }
}

data class ErrorResponse(val message: String?)