package com.tinkoff.course_work.integration

@Suppress("PrivatePropertyName")
class URLBuilder {
    private val BASE_URL = "https://image-charts.com/chart?"

    private val VALUE_SHADOW = "chd=a"
    private val NAME_SHADOW = "chl="
    private val TITLE_SHADOW = "chtt="
    private val SIZE_SHADOW = "chs="
    private val PIE_TYPE = "cht=p"

    private val COLON = "%3A"
    private val COMMA = "%2C"
    private val HATCH = "%7C"
    private val SPACE = "%20"
    private val SIZE_SEP = "x"
    private val SEPARATOR = "&"

    private var result = BASE_URL

    fun build() = result

    fun addValues(values: List<Number>): URLBuilder {
        result += VALUE_SHADOW + COLON
        addToResult(values, COMMA)
        result += SEPARATOR
        return this
    }

    fun addNames(names: List<String>): URLBuilder {
        result += NAME_SHADOW
        addToResult(names, HATCH)
        result += SEPARATOR
        return this
    }

    private fun addToResult(values: List<Any>, separator: String) {
        result = values.fold(result) { url, value ->
            url + value.toString() + separator
        }
    }

    fun setTitle(title: String): URLBuilder {
        val words = title.replace(" ", SPACE)
        result += TITLE_SHADOW + words + SEPARATOR
        return this
    }

    fun setPieChartType(): URLBuilder {
        result += PIE_TYPE + SEPARATOR
        return this
    }

    fun setSize(width: Int, height: Int): URLBuilder {
        result += SIZE_SHADOW + width + SIZE_SEP + height + SEPARATOR
        return this
    }
}