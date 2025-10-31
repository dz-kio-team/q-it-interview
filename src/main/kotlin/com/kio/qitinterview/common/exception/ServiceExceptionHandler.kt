package com.kio.qitinterview.common.exception

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 해당 핸들러에서 처리되지 않는 예외는 GlobalExceptionHandler로 전달됩니다
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ServiceExceptionHandler {
}