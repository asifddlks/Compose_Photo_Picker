package com.asifddlks.photopicker.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.asifddlks.photopicker.MyApplication
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Uri.asFile(fileName:String? = null): File {

    //val downloadsDirectory = MyApplication.applicationContext().getExternalFilesDir(null)
    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    val destinationFile = File(downloadsDirectory, fileName ?: "saved_photo.jpg")
    val inputStream: InputStream? = MyApplication.applicationContext().contentResolver.openInputStream(this)
    val outputStream: FileOutputStream = FileOutputStream(destinationFile)

    inputStream?.use { input ->
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024) // Adjust if you want a different buffer size
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
            output.flush()
        }
    }
    return destinationFile
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss", Locale.getDefault()).format(Date())
    val imageFileName = timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}
