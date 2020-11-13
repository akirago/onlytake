package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication
import java.util.*


const val LOCALE = "LOCALE"
const val FORMAT = "FORMAT"

fun setLocale(locale: LOCALES) {
    editPreference()
        .putString(LOCALE, locale.key)
}

fun getLocale() : Locale = localeMap[getPreference().getString(LOCALE, LOCALES.US.key)] ?: Locale.US

fun setFormat(format: FILE_FORMATS) {
    editPreference()
        .putString(FORMAT, format.format)
}

fun getFormat(): String = getPreference().getString(FORMAT, FILE_FORMATS.FILE_FORMAT_SEC.format) ?: FILE_FORMATS.FILE_FORMAT_SEC.format

fun editPreference(): SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(CustomApplication.instance).edit()
fun getPreference(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomApplication.instance)
