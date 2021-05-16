package com.tinkoff.course_work.models.json.ordinary

import com.tinkoff.course_work.models.json.BasicJson
import java.time.LocalDateTime

interface OrdinaryJson : BasicJson {
    val date: LocalDateTime?
}