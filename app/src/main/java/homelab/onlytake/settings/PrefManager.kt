package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication
import java.util.*


const val LOCALE = "LOCALE"
const val FORMAT = "FORMAT"

fun setLocale(locale: String) {
    editPreference()
        .putString(LOCALE, locale)
}

fun getLocale() : Locale = localeMap[getPreference().getString(LOCALE, US)] ?: Locale.US

fun setFormat(format: String) {
    editPreference()
        .putString(FORMAT, format)
}

fun getFormat(): String = getPreference().getString(FORMAT, FILE_FORMAT_SEC) ?: FILE_FORMAT_SEC

fun editPreference(): SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(CustomApplication.instance).edit()
fun getPreference(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomApplication.instance)
