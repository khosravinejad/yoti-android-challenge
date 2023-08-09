package com.yoti.android.cryptocurrencychallenge.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    private val calendar = Calendar.getInstance(TimeZone.getDefault())
    fun getFormattedLocalDate(timeInMillis: Long): String {
        calendar.timeInMillis = timeInMillis
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
    }
}