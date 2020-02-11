import com.google.gson.annotations.SerializedName

data class PrayerData(
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String // OK
)

data class Data(
    @SerializedName("timings")
    val timings: Timings
)

data class Timings(
    @SerializedName("Asr")
    val asr: String, // 16:00
    @SerializedName("Dhuhr")
    val dhuhr: String, // 12:39
    @SerializedName("Fajr")
    val fajr: String, // 05:34
    @SerializedName("Imsak")
    val imsak: String, // 05:24
    @SerializedName("Isha")
    val isha: String, // 19:44
    @SerializedName("Maghrib")
    val maghrib: String, // 18:32
    @SerializedName("Midnight")
    val midnight: String, // 00:39
    @SerializedName("Sunrise")
    val sunrise: String, // 06:46
    @SerializedName("Sunset")
    val sunset: String // 18:32
)