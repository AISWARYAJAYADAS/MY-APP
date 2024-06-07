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
//data class GalleryFolder(
//    var name: String,
//    val folderPath: String,
//    var firstPicPath: String,
//    var isSelected: Boolean
//) {
//    var picCount = 0
//        private set
//
//    fun addPics() {
//        picCount++
//    }
//}

/**
 * This class represents a gallery folder with the following properties:
 *
 * name: The name of the folder.
 * folderPath: The path of the folder.
 * firstImagePath: The path to the first image in the folder.
 * imageCount: The count of images in the folder, initialized to 0.
 *
 * **/

data class GalleryFolder(
    val name: String,
    val folderPath: String,
    var firstImagePath: String,
    var imageCount: Int = 0
) {
    fun incrementImageCount() {
        imageCount++
    }
}

