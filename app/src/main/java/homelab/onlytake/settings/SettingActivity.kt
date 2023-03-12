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
import homelab.onlytake.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdvertise(this)
        activateView(binding.adViewInSettings)

        binding.versionName.text = "app version: ${BuildConfig.VERSION_NAME}"

        when (getFormat()) {
            FILE_FORMATS.FILE_FORMAT_SEC -> binding.formatRadioButtonSec
            FILE_FORMATS.FILE_FORMAT_MIN -> binding.formatRadioButtonMin
            FILE_FORMATS.FILE_FORMAT_HOUR -> binding.formatRadioButtonHour
            FILE_FORMATS.FILE_FORMAT_DAY -> binding.formatRadioButtonDay
        }.isChecked = true

        when (getOcrSetting()) {
            OcrSetting.PREFIX -> binding.textRadioButtonPREFIX
            OcrSetting.SUFFIX -> binding.textRadioButtonSUFFIX
            OcrSetting.NONE -> binding.textRadioButtonNONE
        }.isChecked = true

        binding.prefixEditText.setText(getPrefixSetting())
        binding.suffixEditText.setText(getSuffixSetting())

        binding.formatRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Responds to child RadioButton checked/unchecked
            when (checkedId) {
                binding.formatRadioButtonSec.id -> setFormat(FILE_FORMATS.FILE_FORMAT_SEC)
                binding.formatRadioButtonMin.id -> setFormat(FILE_FORMATS.FILE_FORMAT_MIN)
                binding.formatRadioButtonHour.id -> setFormat(FILE_FORMATS.FILE_FORMAT_HOUR)
                binding.formatRadioButtonDay.id -> setFormat(FILE_FORMATS.FILE_FORMAT_DAY)
            }
        }

        binding.textRecognizeRadioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.textRadioButtonPREFIX.id -> setOcrSetting(OcrSetting.PREFIX)
                binding.textRadioButtonSUFFIX.id -> setOcrSetting(OcrSetting.SUFFIX)
                binding.textRadioButtonNONE.id -> setOcrSetting(OcrSetting.NONE)
            }
        }

        binding.prefixEditText.doOnTextChanged { text, _, _, _ ->
            setPrefixSetting(text.toString())
        }

        binding.suffixEditText.doOnTextChanged { text, _, _, _ ->
            setSuffixSetting(text.toString())
        }

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButton.setOnClickListener {
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