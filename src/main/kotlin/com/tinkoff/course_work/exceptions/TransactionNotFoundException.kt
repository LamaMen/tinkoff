package com.tinkoff.course_work.exceptions

import com.tinkoff.course_work.dao.OrdinaryMoneyTransactionDAO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class TransactionNotFoundException(id: Int?) : RuntimeException() {
    private val logger = LoggerFactory.getLogger(OrdinaryMoneyTransactionDAO::class.java)

    init {
        logger.info("Transaction with ID=$id not found")
    }
}