package homelab.onlytake

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import homelab.onlytake.advertise.activateView
import homelab.onlytake.advertise.initAdvertise
import homelab.onlytake.file.*
import homelab.onlytake.settings.OcrSetting
import homelab.onlytake.settings.SettingActivity
import homelab.onlytake.settings.getOcrSetting
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.min


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    val recognizer = TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdvertise(this)
        activateView(ad_view)


        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
        camera_capture_button.setOnClickListener {
            takePhoto()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SettingActivity::class.java))
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun takePhoto() {   // Get a stable reference of the modifiable image capture use case
        progress_bar.isVisible = true
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = createFile()

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    progress_bar.isGone = true
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = FileProvider.getUriForFile(
                        this@MainActivity,
                        this@MainActivity.applicationContext.packageName.toString() + ".provider",
                        photoFile
                    )
                    if (getOcrSetting() == OcrSetting.NONE) {
                        shareGoogleDrive(photoFile)
                        return
                    }
                    val image: InputImage
                    try {
                        image = InputImage.fromFilePath(this@MainActivity, savedUri)
                    } catch (e: IOException) {
                        Log.e("tag", e.message ?: "")
                        return
                    }
                    recognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            // Task completed successfully
                            val yens = mutableListOf<Int>()
                            val mayYens = mutableListOf<Int>()
                            var useText = ""
                            var height = 0
                            for (block in visionText.textBlocks) {
                                for (line in block.lines) {
                                    val lineText = line.text
                                    Log.d("recog text lineText", lineText)
                                    Log.d("recog text getYen", getYen(lineText).toString())
                                    if (lineText.contains("円") || lineText.contains("¥")) {
                                        yens.add(getYen(lineText))
                                        continue
                                    }
                                    if (getYen(lineText) != 0) {
                                        mayYens.add(getYen(lineText))
                                    }
                                }
                            }
                            yens.sortDescending()
                            mayYens.sortDescending()
                            val res = mutableListOf<String>().apply {
                                addAll(yens.slice(0..min(2, yens.size - 1))
                                    .map {
                                        it.toString()
                                    })
                                addAll(mayYens.slice(0..min(2, mayYens.size - 1))
                                    .map {
                                        it.toString()
                                    })
                                add("None")
                            }.toTypedArray()
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle("select total amount")
                                .setItems(
                                    res
                                ) { _, which ->
                                    var file = photoFile
                                    val result = res[which]
                                    if (result == "None") {
                                        shareGoogleDrive(file)
                                        return@setItems
                                    }
                                    when (getOcrSetting()) {
                                        OcrSetting.PREFIX -> file = photoFile.addPrefix(result)
                                        OcrSetting.SUFFIX -> file = photoFile.addSuffix(result)
                                        OcrSetting.NONE -> {}
                                    }
                                    shareGoogleDrive(file)
                                    Log.d("recog text useText", result)
                                }.create().show()
                        }
                        .addOnFailureListener { e ->
                            shareGoogleDrive(photoFile)
                        }

                    val msg = "Photo capture succeeded: $savedUri"
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }

    private fun getYen(text: String): Int {
        var result = ""
        text.forEach {
            if (it.isDigit()) result += it
            if (result.length > 8) return 0
        }
        var resInt = 0
        if (result.isEmpty()) return resInt
        if (result.toInt() in 101..999999) {
            resInt = result.toInt()
        }
        return resInt
    }

    private fun shareGoogleDrive(file: File) {
        progress_bar.isGone = true

        val savedUri = FileProvider.getUriForFile(
            this@MainActivity,
            this@MainActivity.applicationContext.packageName.toString() + ".provider",
            file
        )
        val shareIntent = ShareCompat.IntentBuilder.from(this@MainActivity)
            .setText("Share png")
            .setType("image/png")
            .setStream(savedUri)
            .intent
            .setPackage("com.google.android.apps.docs")
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(shareIntent)

    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}
