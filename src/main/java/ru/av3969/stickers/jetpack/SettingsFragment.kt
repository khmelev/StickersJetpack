package ru.av3969.stickers.jetpack

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when(preference.key) {
            "nightMode" -> {
                restartActivityWithDelay()
                return true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }

    private fun restartActivityWithDelay() {
        GlobalScope.launch (Dispatchers.Main) {
            delay(500)
            activity?.recreate()
        }
    }
}
