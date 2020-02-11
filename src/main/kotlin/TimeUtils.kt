import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    fun parseTime(time: String): Date {
        return TIME_FORMAT.parse("$time ${DATE_FORMAT.format(now)}")
    }

    fun getDuration(durationInMillis: Long): String? {

        return when (val seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis)) {
            0L, 1L -> "$seconds second"
            in 1..59 -> "$seconds seconds"
            60L -> "1 minute"
            in 61..3599 -> {
                // minute and seconds
                val min = seconds / 60
                val sec = seconds % 60
                "$min min $sec sec."
            }

            3600L -> "1 hour"
            in 3601..86399 -> {
                // hour and minute
                val hour = seconds / 3600
                val minute = (seconds % 3600) / 60
                "$hour hrs $minute min"
            }

            else -> {
                null
            }
        }
    }
}