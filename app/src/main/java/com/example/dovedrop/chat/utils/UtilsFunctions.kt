package com.example.dovedrop.chat.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun getReadableDate(dateInitial: Long?): String {
    if(dateInitial == null) return ""
    val date = Date(dateInitial)

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val formattedDate = dateFormatter.format(date)
    val formattedTime = timeFormatter.format(date)

    val calendar = Calendar.getInstance().apply { time = date }
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    when {
        calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> {
            return formattedTime
        }

        calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> {
            return "Yesterday"
        }

        else -> {
            return formattedDate
        }
    }
}

fun getReadableDateTwo(dateInitial: Long?) : String {
    if(dateInitial == null) return ""
    val date = Date(dateInitial)

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val formattedDate = dateFormatter.format(date)

    val calendar = Calendar.getInstance().apply { time = date }
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    return when {
        calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> {
            "Today"
        }

        calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> {
            "Yesterday"
        }

        else -> {
            formattedDate
        }
    }
}

fun getDateAndTime(timeInMillis: Long): Pair<String,String> {
    val date = Date(timeInMillis)
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return Pair(dateFormatter.format(date), timeFormatter.format(date))
}