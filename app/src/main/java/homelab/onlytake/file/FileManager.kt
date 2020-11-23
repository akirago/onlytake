package homelab.onlytake.file

import homelab.onlytake.CustomApplication
import homelab.onlytake.R
import homelab.onlytake.settings.getFormat
import homelab.onlytake.settings.getLocale
import java.io.File
import java.text.SimpleDateFormat

fun getOutputDirectory(): File {
    val mediaDir = CustomApplication.context.externalMediaDirs.firstOrNull()?.let {
        File(
            it,
            CustomApplication.context.resources.getString(R.string.app_name)
        ).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else CustomApplication.context.filesDir
}

fun createFile() = File(
    getOutputDirectory(),
    createFilePath()
)

fun createFilePath() = SimpleDateFormat(
    getFormat().format, getLocale().locale
).format(System.currentTimeMillis()).toJpg()

fun String.toJpg(): String = "$this.jpg"

fun String.trimJpg(): String = substring(0, length - 4)

fun File.addPrefix(prefix: String): File {
    val file = File(getOutputDirectory(), "${prefix}_$name")
    renameTo(file)
    return file
}

fun File.addSuffix(suffix: String): File {
    val file = File(getOutputDirectory(), "${name.trimJpg()}_$suffix".toJpg())
    if (exists()) renameTo(file)
    return file
}