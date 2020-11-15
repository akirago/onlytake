package homelab.onlytake.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import homelab.onlytake.R
import homelab.onlytake.advertise.activateView
import homelab.onlytake.advertise.initAdvertise
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initAdvertise(this)
        activateView(ad_view_in_settings)

        when (getFormat()) {
            FILE_FORMATS.FILE_FORMAT_SEC -> format_radio_button_sec
            FILE_FORMATS.FILE_FORMAT_MIN -> format_radio_button_min
            FILE_FORMATS.FILE_FORMAT_HOUR -> format_radio_button_hour
        }.isChecked = true

        when (getLocale()) {
            LOCALES.CANADA -> locale_radio_button_CANADA
            LOCALES.CHINA -> locale_radio_button_CHINA
            LOCALES.FRANCE -> locale_radio_button_FRANCE
            LOCALES.GERMAN -> locale_radio_button_GERMAN
            LOCALES.ITALY -> locale_radio_button_ITALY
            LOCALES.JAPAN -> locale_radio_button_JAPAN
            LOCALES.KOREA -> locale_radio_button_KOREA
            LOCALES.TAIWAN -> locale_radio_button_TAIWAN
            LOCALES.UK -> locale_radio_button_UK
            LOCALES.US -> locale_radio_button_US
        }.isChecked = true

        format_radio_group.setOnCheckedChangeListener { _, checkedId ->
            // Responds to child RadioButton checked/unchecked
            when (checkedId) {
                format_radio_button_sec.id -> setFormat(FILE_FORMATS.FILE_FORMAT_SEC)
                format_radio_button_min.id -> setFormat(FILE_FORMATS.FILE_FORMAT_MIN)
                format_radio_button_hour.id -> setFormat(FILE_FORMATS.FILE_FORMAT_HOUR)
            }
        }

        locale_radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                locale_radio_button_CANADA.id -> setLocale(LOCALES.CANADA)
                locale_radio_button_CHINA.id -> setLocale(LOCALES.CHINA)
                locale_radio_button_FRANCE.id -> setLocale(LOCALES.FRANCE)
                locale_radio_button_GERMAN.id -> setLocale(LOCALES.GERMAN)
                locale_radio_button_ITALY.id -> setLocale(LOCALES.ITALY)
                locale_radio_button_JAPAN.id -> setLocale(LOCALES.JAPAN)
                locale_radio_button_KOREA.id -> setLocale(LOCALES.KOREA)
                locale_radio_button_TAIWAN.id -> setLocale(LOCALES.TAIWAN)
                locale_radio_button_UK.id -> setLocale(LOCALES.UK)
                locale_radio_button_US.id -> setLocale(LOCALES.US)
            }
        }

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        save_button.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}