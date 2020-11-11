package homelab.onlytake.settings

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import homelab.onlytake.CustomApplication

fun setLocale() {
    editPreference()
}


fun editPreference(): SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(CustomApplication.instance).edit()
