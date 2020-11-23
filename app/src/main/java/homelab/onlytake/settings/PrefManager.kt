package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication


const val PREF_LOCALE = "LOCALE"
fun setLocale(locale: LOCALES) {
    editPreference()
        .putString(PREF_LOCALE, locale.key)
        .commit()
}

fun getLocale() = getLocaleFromKey(getPreference().getString(PREF_LOCALE, LOCALES.US.key))


const val PREF_FORMAT = "FORMAT"
fun setFormat(format: FILE_FORMATS) {
    editPreference()
        .putString(PREF_FORMAT, format.format)
        .commit()
}

fun getFormat() =
    getFileFormatsFromKey(getPreference().getString(PREF_FORMAT, FILE_FORMATS.FILE_FORMAT_SEC.format))

const val PREF_OCR = "OCR"
fun setOcrSetting(ocrSetting: OcrSetting) {
    editPreference()
        .putString(PREF_OCR, ocrSetting.key)
        .commit()
}


fun getOcrSetting() = getOcrSettingFromKey(getPreference().getString(PREF_OCR, OcrSetting.NONE.key))

const val PREF_PREFIX = "PREFIX"
fun setPrefixSetting(prefix: String) = setPreferenceOfString(PREF_PREFIX, prefix)
fun getPrefixSetting() = getPreference().getString(PREF_PREFIX, "")!!

const val PREF_SUFFIX = "SUFFIX"
fun setSuffixSetting(suffix: String) = setPreferenceOfString(PREF_SUFFIX, suffix)
fun getSuffixSetting() = getPreference().getString(PREF_SUFFIX, "")!!


fun setPreferenceOfString(key: String, value: String) {
    editPreference()
        .putString(key, value)
        .commit()
}


fun editPreference(): SharedPreferences.Editor =
    PreferenceManager.getDefaultSharedPreferences(CustomApplication.context).edit()

fun getPreference(): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(CustomApplication.context)
