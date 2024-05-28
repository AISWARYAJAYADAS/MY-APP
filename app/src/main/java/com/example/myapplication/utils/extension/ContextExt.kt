package com.example.myapplication.utils.extension

import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import com.example.myapplication.utils.NetworkConstants
import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.errorCodeDetails(): Triple<Int, String, String>? {
    try {
        val responseJson = JSONObject(this.string())
        if (responseJson.has(NetworkConstants.STATUS_CODE)) {
            val statusCode = responseJson.getInt(NetworkConstants.STATUS_CODE)
            var message = ""
            var errorMessageFromApi = ""
            if (responseJson.has(NetworkConstants.STATUS_MESSAGE)) {
                message = responseJson.getString(NetworkConstants.STATUS_MESSAGE)
            }
            if (responseJson.has(NetworkConstants.ERROR_MESSAGE_FROM_API)) {
                errorMessageFromApi =
                    responseJson.getString(NetworkConstants.ERROR_MESSAGE_FROM_API)
            }
            return Triple(statusCode, message, errorMessageFromApi)
        }
    } catch (ex: Exception) {
        Log.e(this::class.java.simpleName, ex.message.toString())
    }
    return null
}

fun createHtmlTabHeading(
    title: String, count: String? = "", titleTextColor: String = "#212121",
    countTextColor: String = "#A7A298",
): Spanned {
    return if (count.isNullOrBlank()) {
        Html.fromHtml(
            "<font color='${titleTextColor}'>${title}</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    } else {
        Html.fromHtml(
            "<font color='${titleTextColor}'>${title}</font>" + "<font color='${countTextColor}'> (${count})</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

    }
}