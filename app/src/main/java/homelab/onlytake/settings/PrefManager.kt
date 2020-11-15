package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication


const val LOCALE = "LOCALE"
const val FORMAT = "FORMAT"

fun setLocale(locale: LOCALES) {
    editPreference()
        .putString(LOCALE, locale.key)
}

fun getLocale() = getLocaleFromKey(getPreference().getString(LOCALE, LOCALES.US.key))

fun setFormat(format: FILE_FORMATS) {
    editPreference()
        .putString(FORMAT, format.format)
        .commit()
}

fun getFormat() = getFileFormatsFromKey(getPreference().getString(FORMAT, FILE_FORMATS.FILE_FORMAT_SEC.format))

fun editPreference(): SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(CustomApplication.context).edit()
fun getPreference(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomApplication.context)
