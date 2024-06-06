package com.example.myapplication.gallery

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

data class Gallery(
    val id: Long,
    val itemUrl: String
)


/**
 *  Create a data class to represent the gallery folder, including properties such as folder name, folder path, and number of images.
 **/
//data class GalleryFolder(val name: String, val path: String, val imageCount: Int)
data class GalleryFolder(
    val name: String,
    val folderPath: String,
    var firstPicPath: String,
    var isSelected: Boolean
) {
    var picCount = 0
        private set

    fun addPics() {
        picCount++
    }
}
