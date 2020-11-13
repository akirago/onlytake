package homelab.onlytake.settings

import homelab.onlytake.settings.LOCALES.*
import java.util.*


// for file format date
enum class FILE_FORMATS(val format: String) {
    FILE_FORMAT_SEC("yyyy-MM-dd-HH-mm-ss"),
    FILE_FORMAT_MIN("yyyy-MM-dd-HH-mm"),
    FILE_FORMAT_HOUR("yyyy-MM-dd-HH"),
}

// for LOCALE
enum class LOCALES(val key: String) {
    CANADA("CANADA"),
    CHINA("CHINA"),
    FRANCE("FRANCE"),
    GERMAN("GERMAN"),
    ITALY("ITALY"),
    JAPAN("JAPAN"),
    KOREA("KOREA"),
    TAIWAN("TAIWAN"),
    UK("UK"),
    US("US"),
}

val localeMap = mapOf<String, Locale>(
    CANADA.key to Locale.CANADA,
    CHINA.key to Locale.CHINA,
    FRANCE.key to Locale.FRANCE,
    GERMAN.key to Locale.GERMAN,
    ITALY.key to Locale.ITALY,
    JAPAN.key to Locale.JAPAN,
    KOREA.key to Locale.KOREA,
    TAIWAN.key to Locale.TAIWAN,
    UK.key to Locale.UK,
    US.key to Locale.US,
)