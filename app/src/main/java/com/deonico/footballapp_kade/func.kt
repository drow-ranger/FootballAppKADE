package com.deonico.footballapp_kade

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun strToDate(strDate: String?, pattern: String = "yyyy-MM-dd"): Date{
    val locale = Locale("ID")
    val format = SimpleDateFormat(pattern, locale)

    return format.parse(strDate)
}

@SuppressLint("SimpleDateFormat")
fun changeFormatDate(date: Date?): String? = with(date ?: Date()){
    val locale = Locale("ID")
    SimpleDateFormat("EEE, dd MMM yyy", locale).format(this)
}