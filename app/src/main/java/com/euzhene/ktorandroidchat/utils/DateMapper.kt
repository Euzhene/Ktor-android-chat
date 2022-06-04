package com.euzhene.ktorandroidchat.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun toAMPMFieldDate(timestamp: Long): String {
    val date = getDate(timestamp)
    val sdf = SimpleDateFormat("HH:MM a", Locale.getDefault())
    return sdf.format(date)
}

fun toDefaultDate(timestamp: Long): String {
    val date = getDate(timestamp)
    return DateFormat.getDateInstance().format(date)
}

private fun getDate(timestamp: Long): Date {
    val timestampMillis = timestamp * MILLIS_IN_SECOND
    return Date(timestampMillis)
}

private const val MILLIS_IN_SECOND = 1000