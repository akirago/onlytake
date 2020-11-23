package homelab.onlytake.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import homelab.onlytake.BuildConfig
import homelab.onlytake.R
import homelab.onlytake.advertise.activateView
import homelab.onlytake.advertise.initAdvertise
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initAdvertise(this)
        activateView(ad_view_in_settings)

        version_name.text = "app version: ${BuildConfig.VERSION_NAME}"

        when (getFormat()) {
            FILE_FORMATS.FILE_FORMAT_SEC -> format_radio_button_sec
            FILE_FORMATS.FILE_FORMAT_MIN -> format_radio_button_min
            FILE_FORMATS.FILE_FORMAT_HOUR -> format_radio_button_hour
        }.isChecked = true

        when (getOcrSetting()) {
            OcrSetting.PREFIX -> text_radio_button_PREFIX
            OcrSetting.SUFFIX -> text_radio_button_SUFFIX
            OcrSetting.NONE -> text_radio_button_NONE
        }.isChecked = true

        prefix_edit_text.setText(getPrefixSetting())
        suffix_edit_text.setText(getSuffixSetting())

        format_radio_group.setOnCheckedChangeListener { _, checkedId ->
            // Responds to child RadioButton checked/unchecked
            when (checkedId) {
                format_radio_button_sec.id -> setFormat(FILE_FORMATS.FILE_FORMAT_SEC)
                format_radio_button_min.id -> setFormat(FILE_FORMATS.FILE_FORMAT_MIN)
                format_radio_button_hour.id -> setFormat(FILE_FORMATS.FILE_FORMAT_HOUR)
            }
        }

        text_recognize_radio_group.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                text_radio_button_PREFIX.id -> setOcrSetting(OcrSetting.PREFIX)
                text_radio_button_SUFFIX.id -> setOcrSetting(OcrSetting.SUFFIX)
                text_radio_button_NONE.id -> setOcrSetting(OcrSetting.NONE)
            }
        }

        prefix_edit_text.doOnTextChanged { text, _, _, _ ->
            setPrefixSetting(text.toString())
        }

        suffix_edit_text.doOnTextChanged { text, _, _, _ ->
            setSuffixSetting(text.toString())
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