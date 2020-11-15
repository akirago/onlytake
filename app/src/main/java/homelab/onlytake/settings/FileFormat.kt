package homelab.onlytake.settings

import homelab.onlytake.settings.FILE_FORMATS.*
import homelab.onlytake.settings.LOCALES.*
import java.util.*


// for file format date
enum class FILE_FORMATS(val format: String) {
    FILE_FORMAT_SEC("yyyy-MM-dd-HH-mm-ss"),
    FILE_FORMAT_MIN("yyyy-MM-dd-HH-mm"),
    FILE_FORMAT_HOUR("yyyy-MM-dd-HH"),
}

// for LOCALE
enum class LOCALES(val key: String, val locale: Locale) {
    CANADA("CANADA", Locale.CANADA),
    CHINA("CHINA", Locale.CHINA),
    FRANCE("FRANCE", Locale.FRANCE),
    GERMAN("GERMAN", Locale.GERMAN),
    ITALY("ITALY", Locale.ITALY),
    JAPAN("JAPAN", Locale.JAPAN),
    KOREA("KOREA", Locale.KOREA),
    TAIWAN("TAIWAN", Locale.TAIWAN),
    UK("UK", Locale.UK),
    US("US", Locale.US),
}

fun getFileFormatsFromKey(key: String?): FILE_FORMATS = fileMap[key] ?: FILE_FORMAT_SEC

private val fileMap = mapOf(
    FILE_FORMAT_SEC.format to FILE_FORMAT_SEC,
    FILE_FORMAT_MIN.format to FILE_FORMAT_MIN,
    FILE_FORMAT_HOUR.format to FILE_FORMAT_HOUR,
)

fun getLocaleFromKey(key: String?): LOCALES = localeMap[key] ?: US

private val localeMap = mapOf(
    CANADA.key to CANADA,
    CHINA.key to CHINA,
    FRANCE.key to FRANCE,
    GERMAN.key to GERMAN,
    ITALY.key to ITALY,
    JAPAN.key to JAPAN,
    KOREA.key to KOREA,
    TAIWAN.key to TAIWAN,
    UK.key to UK,
    US.key to US,
)