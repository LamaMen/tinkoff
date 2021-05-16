package com.tinkoff.course_work.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class TransactionNotFoundException(id: Int?) : RuntimeException() {
    private val logger = LoggerFactory.getLogger(TransactionNotFoundException::class.java)

    init {
        logger.info("Transaction with ID=$id not found")
    }
}