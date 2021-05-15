package com.tinkoff.course_work.exceptions

import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class BadRequestException(override val message: String?) : RuntimeException() {
    private val logger = LoggerFactory.getLogger(JsonService::class.java)

    init {
        logger.info(message)
    }
}