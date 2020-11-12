package homelab.onlytake.settings

import java.util.*


// for file format date
val FILE_FORMAT_SEC = "yyyy-MM-dd-HH-mm-ss"
val FILE_FORMAT_MIN = "yyyy-MM-dd-HH-mm"
val FILE_FORMAT_HOUR = "yyyy-MM-dd-HH"

// for LOCALE
val CANADA = "CANADA"
val CHINA = "CHINA"
val FRANCE = "FRANCE"
val GERMAN = "GERMAN"
val ITALY = "ITALY"
val JAPAN = "JAPAN"
val KOREA = "KOREA"
val TAIWAN = "TAIWAN"
val UK = "UK"
val US = "US"

val localeMap = mapOf<String, Locale>(
    CANADA to Locale.CANADA,
    CHINA to Locale.CHINA,
    FRANCE to Locale.FRANCE,
    GERMAN to Locale.GERMAN,
    ITALY to Locale.ITALY,
    JAPAN to Locale.JAPAN,
    KOREA to Locale.KOREA,
    TAIWAN to Locale.TAIWAN,
    UK to Locale.UK,
    US to Locale.US,
)