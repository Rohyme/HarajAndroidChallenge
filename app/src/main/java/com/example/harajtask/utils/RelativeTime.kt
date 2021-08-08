package com.example.harajtask.utils

object RelativeTime {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val MONTH_MILLIS = 30L * DAY_MILLIS


    fun getTimeAgo(t: Long): String? {
        var time = t
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now: Long = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS ->  "just now"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"

            diff < 50 * MINUTE_MILLIS -> " ${diff.div(MINUTE_MILLIS)} minutes ago"

            diff < 90 * MINUTE_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff.div( HOUR_MILLIS)} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            diff < 30L * DAY_MILLIS -> "${diff.div( DAY_MILLIS)} days ago"
            diff < 2 * MONTH_MILLIS -> "about a month ago"
            diff < 12 * MONTH_MILLIS -> "about ${diff.div( MONTH_MILLIS).toInt()} months ago"
            else -> {
                "${diff.div( MONTH_MILLIS).toInt()} months ago"
            }
        }
    }
}