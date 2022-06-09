package com.euzhene.ktorandroidchat.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun toAMPMFieldDate(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(date)
}

fun toDefaultDate(timestamp: Long): String {
    val date = Date(timestamp)
    return DateFormat.getDateInstance().format(date)
}
