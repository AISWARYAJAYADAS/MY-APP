package com.example.myapplication.gallery

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GalleryPickerViewModel @Inject constructor(
    private val cr: ContentResolver?,
) : ViewModel() {

    var galleryClickedItem = MutableLiveData<String>()
    val galleryFolders = mutableStateOf<List<GalleryFolder>>(emptyList())

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    val galleryFolderList = MutableLiveData<ArrayList<GalleryFolder>>()
    val currentMediaFolder = MutableLiveData<String>()

    fun getGalleryFolders() {
        coroutineScope.launch {
            val mediaFolderList = getMediaFolders()
            galleryFolderList.postValue(mediaFolderList)
            galleryFolders.value = mediaFolderList
            if (mediaFolderList.isEmpty()) {
                currentMediaFolder.postValue("")
            }
        }
    }

    suspend fun getMediaFolders(): ArrayList<GalleryFolder> {
        val mediaFolders: ArrayList<GalleryFolder> = ArrayList()
        withContext(Dispatchers.IO) {
            val picPaths = ArrayList<String>()
            val mediaUri = MediaStore.Files.getContentUri("external")

            val projection = arrayOf(
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
            )

            val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=" +
                    "${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE} AND (" +
                    "${MediaStore.Images.Media.MIME_TYPE}=? OR " +
                    "${MediaStore.Images.Media.MIME_TYPE}=? OR " +
                    "${MediaStore.Images.Media.MIME_TYPE}=?)"
            val selectionArgs = arrayOf("image/jpeg", "image/png", "image/jpg")

            val cursor = cr?.query(mediaUri, projection, selection, selectionArgs, null)
            cursor?.moveToFirst()
            cursor?.let {
                do {
                    try {
                        val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                        val dataPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                        var folderPaths = dataPath.substring(0, dataPath.lastIndexOf("$folder/"))
                        folderPaths = "$folderPaths$folder/"
                        if (!picPaths.contains(folderPaths)) {
                            picPaths.add(folderPaths)
                            val item = GalleryFolder(folder, folderPaths, dataPath, false)
                            item.addPics()
                            mediaFolders.add(item)
                        } else {
                            for (i in mediaFolders.indices) {
                                if (mediaFolders[i].folderPath == folderPaths) {
                                    mediaFolders[i].firstPicPath = dataPath
                                    mediaFolders[i].addPics()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (cursor.moveToNext())
                cursor.close()
            }
        }
        return mediaFolders
    }
}



