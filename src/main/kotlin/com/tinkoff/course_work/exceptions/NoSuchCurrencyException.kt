package com.tinkoff.course_work.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NoSuchCurrencyException(message: String) : RuntimeException() {
    private val logger = LoggerFactory.getLogger(NoSuchCurrencyException::class.java)

    init {
        logger.info(message)
    }
}