package homelab.onlytake.cloth.register

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import homelab.onlytake.CustomApplication
import homelab.onlytake.R
import homelab.onlytake.database.AppDatabase
import homelab.onlytake.database.Cloth
import homelab.onlytake.database.Genre
import homelab.onlytake.databinding.ActivityTakeClothesBinding
import homelab.onlytake.genre.RegisterGenreViewModel
import homelab.onlytake.genre.RegisterGenreViewModelFactory
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class TakeClothesActivity : AppCompatActivity() {

    lateinit var binding: ActivityTakeClothesBinding


    private val takeClothesViewModel: TakeClothesViewModel by viewModels {
        TakeClothesViewModelFactory((application as CustomApplication).takeClothesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding.captureButton.setOnClickListener {
            takePhoto()
        }
    }

    lateinit var imageCapture: ImageCapture

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun createFile(baseFolder: File, format: String, extension: String): File {
        val timeStamp = SimpleDateFormat(format, Locale.US).format(System.currentTimeMillis())
        return File(baseFolder, "$timeStamp$extension")
    }

    private fun takePhoto() {
        val photoFile = createFile(application.filesDir, FILENAME, PHOTO_EXTENSION)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: return
                    val bitmap = BitmapFactory.decodeFile(savedUri.path)

                    // Fix the orientation
                    val correctedBitmap = correctBitmapOrientation(savedUri.path!!, bitmap)

                    // Display the corrected bitmap
                    showResultContainer(correctedBitmap)
                }
            })
    }

    fun correctBitmapOrientation(filePath: String, bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(filePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun showResultContainer(bitmap: Bitmap? = null) {
        if (bitmap == null) return
        binding.previewContainer.isGone = true
        binding.resultContainer.isVisible = true

        bitmap.let {
            binding.clothView.setImageBitmap(it)
        }

        var selectedGenre = Genre(0, "")

        lifecycleScope.launch {
            takeClothesViewModel.allGenre.collect { genres ->
                (binding.autoComplete as? MaterialAutoCompleteTextView)?.apply {
                    setSimpleItems(genres.map { it.name }.toTypedArray())
                    setOnItemClickListener { _, _, position, _ ->
                        selectedGenre = genres[position]
                    }
                }

            }
        }

        binding.registerClothButton.setOnClickListener {
            lifecycleScope.launch {
                val byteArray = bitmapToByteArray(bitmap)
                val cloth = Cloth(
                    id = 0,
                    name = binding.inputTitle.text.toString(),
                    display = "Sample Display",
                    type = "Sample Type",
                    used_count = 0,
                    genre_id = selectedGenre.id,
                    picture = byteArray
                )

                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
                db.clothDao().insert(cloth)
                Toast.makeText(this@TakeClothesActivity, "登録しました", 2).show()
                finish()
            }
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, stream)
        return stream.toByteArray()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
