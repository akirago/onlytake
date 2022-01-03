package homelab.onlytake.ocr

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions

val recognizer = TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())

