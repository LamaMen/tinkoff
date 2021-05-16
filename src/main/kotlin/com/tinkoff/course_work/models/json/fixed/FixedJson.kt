package com.tinkoff.course_work.models.json.fixed

import com.tinkoff.course_work.models.json.BasicJson

interface FixedJson : BasicJson {
    val day: Int
}