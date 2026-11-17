package ru.laert.core.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import android.content.Context

object FileDownloader {
    private val client = OkHttpClient.Builder().build()

    suspend fun download(context: Context, url: String, fileName: String): File = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Ошибка скачивания: ${response.code}")

            val body = response.body ?: throw Exception("Пустой ответ")
            val file = File(context.cacheDir, fileName)

            file.outputStream().use { output ->
                body.byteStream().copyTo(output)
            }
            file
        }
    }
}