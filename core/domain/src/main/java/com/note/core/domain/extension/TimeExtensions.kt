package com.note.core.domain.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val FORMAT_RECEIVE_DATE_TIME = "yyyy-MM-DD'T'HH:mm:ss'Z'"
private const val FORMAT_CONVERT_DATE_TIME = "yyyy-MM-DD HH:mm"
private const val FORMAT_CONVERT_DATE = "yyyy-MM-dd"
private const val FORMAT_HOURS_DIFFERENCE = "시간 전"
private const val FORMAT_DAYS_DIFFERENCE = "시간 전"

fun getNowTimeStamp(): String{
    return  SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault()).format(Date())
}

fun String.convertCurrentDateTime(): String {
    val receiveFormat = SimpleDateFormat(FORMAT_RECEIVE_DATE_TIME, Locale.getDefault())
    val convertFormat = SimpleDateFormat(FORMAT_CONVERT_DATE_TIME, Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }

    return try {
        val timeStamp = receiveFormat.parse(this)
        timeStamp?.let { convertFormat.format(it) } ?: ""
    } catch (e: ParseException) {
        ""
    }
}

fun Long.getFormatTimeDifference(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val hours = diff / (1000 * 60 * 60)
    val days = diff / (1000 * 60 * 60 * 24)

    return when {
        hours < 24 -> String.format(FORMAT_HOURS_DIFFERENCE, hours)
        days in 1..29 -> String.format(FORMAT_DAYS_DIFFERENCE, days)
        else -> {
            val date = SimpleDateFormat(FORMAT_CONVERT_DATE, Locale.getDefault())
            date.format(Date(this))
        }
    }
}