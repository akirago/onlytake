package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication


const val LOCALE = "LOCALE"
fun setLocale(locale: LOCALES) {
    editPreference()
        .putString(LOCALE, locale.key)
        .commit()
}

fun getLocale() = getLocaleFromKey(getPreference().getString(LOCALE, LOCALES.US.key))


const val FORMAT = "FORMAT"
fun setFormat(format: FILE_FORMATS) {
    editPreference()
        .putString(FORMAT, format.format)
        .commit()
}

fun getFormat() =
    getFileFormatsFromKey(getPreference().getString(FORMAT, FILE_FORMATS.FILE_FORMAT_SEC.format))

const val OCR = "OCR"
fun setOcrSetting(ocrSetting: OcrSetting) {
    editPreference()
        .putString(OCR, ocrSetting.key)
        .commit()
}

fun getOcrSetting() = getOcrSettingFromKey(getPreference().getString(OCR, OcrSetting.NONE.key))




fun editPreference(): SharedPreferences.Editor =
    PreferenceManager.getDefaultSharedPreferences(CustomApplication.context).edit()

fun getPreference(): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(CustomApplication.context)
