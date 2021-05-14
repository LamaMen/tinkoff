package com.tinkoff.course_work.services

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime = this.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime()