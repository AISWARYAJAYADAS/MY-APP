package com.example.myapplication.utils.manager

import android.content.res.Resources
import com.example.myapplication.R
import com.example.myapplication.ui.common.Config
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonResourceManager @Inject constructor(private val resources: Resources?) {

    fun getConfigurationFromJson(): List<Config>? {
        var configurationList: List<Config>? = null
        resources?.let {
            val inputStream: InputStream = resources.openRawResource(R.raw.config)
            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            inputStream.use { ism ->
                val reader: Reader = BufferedReader(InputStreamReader(ism, "UTF-8"))
                var n: Int
                while (reader.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            }
            val jsonString = writer.toString()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, Config::class.java)
            val adapter: JsonAdapter<List<Config>> = moshi.adapter(listType)
            configurationList = adapter.fromJson(jsonString)
        }
        return configurationList
    }

}