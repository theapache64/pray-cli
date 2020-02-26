import TimeUtils.getDuration
import TimeUtils.parseTime
import com.google.gson.Gson
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

// Calc prayer times
val now = Date()
const val dateFormatString = "dd_MM_yyyy"
val DATE_FORMAT = SimpleDateFormat(dateFormatString)
val TIME_FORMAT = SimpleDateFormat("HH:mm $dateFormatString", Locale.ENGLISH)
val TEMP_DIR = File(JarUtils.getJarDir() + ".pray_cli")

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        if (!TEMP_DIR.exists()) {
            TEMP_DIR.mkdir()
        }

        println("Getting prayer time data ...")

        val city = args[0]
        val country = args[1]
        val method = args[2]
        val school = args[3]


        val prayerDataCacheToday =
            File("${TEMP_DIR.absolutePath}/${DATE_FORMAT.format(now)}_${city}_${country}_${method}_${school}.json")

        val gson = Gson()

        val timings = if (prayerDataCacheToday.exists()) {
            val json = prayerDataCacheToday.readText()
            gson.fromJson(json, Timings::class.java)
        } else {

            val prayerJson =
                URL("http://api.aladhan.com/v1/timingsByCity?city=$city&country=$country&method=$method&school=$school").readText()
            val timings = gson.fromJson(prayerJson, PrayerData::class.java).data.timings
            val timingsJson = gson.toJson(timings)
            prayerDataCacheToday.writeText(timingsJson)
            timings
        }

        println(ANSI_WHITE, "-----------------------------")
        println(ANSI_BLUE, "Fajr: ${timings.fajr}")
        println(ANSI_BLUE, "Dhuhr: ${timings.dhuhr}")
        println(ANSI_BLUE, "Asr: ${timings.asr}")
        println(ANSI_BLUE, "Maghrib: ${timings.maghrib}")
        println(ANSI_BLUE, "Isha: ${timings.isha}")
        println(ANSI_WHITE, "-----------------------------")
        println(ANSI_CYAN, "☀️ Sunrise: ${timings.sunrise}")
        println(ANSI_CYAN, "\uD83C\uDF19 Midnight: ${timings.midnight}")
        println(ANSI_CYAN, "Sunset: ${timings.sunset}")
        println(ANSI_CYAN, "Imsak: ${timings.imsak}")
        println(ANSI_WHITE, "-----------------------------")

        val prayerTimes = mutableMapOf<String, Date>()

        prayerTimes["Isha"] = parseTime(timings.isha)
        prayerTimes["Asr"] = parseTime(timings.asr)
        prayerTimes["Maghrib"] = parseTime(timings.maghrib)
        prayerTimes["Dhuhr"] = parseTime(timings.dhuhr)
        prayerTimes["Fajr"] = parseTime(timings.fajr)

        while (true) {

            val timeDistances = mutableMapOf<String, Long>()

            for (prayer in prayerTimes) {
                val diff = prayer.value.time - Date().time

                if (diff > 0) {
                    timeDistances[prayer.key] = diff
                }
            }

            if (timeDistances.isNotEmpty()) {
                val nextPrayer = timeDistances.toList().sortedBy { (_, value) -> value }.toMap().toList().first()
                print(ANSI_GREEN, "\r${nextPrayer.first} in ${getDuration(nextPrayer.second)}")
                Thread.sleep(1000)
            } else {
                break
            }
        }
    }
}
