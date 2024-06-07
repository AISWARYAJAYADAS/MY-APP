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
    private val contentResolver: ContentResolver?
) : ViewModel() {

    var selectedImagePath = MutableLiveData<String>()

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    val galleryFolders = mutableStateOf<List<GalleryFolder>>(emptyList())
    val selectedFolder = mutableStateOf<GalleryFolder?>(null)
    val imagesInSelectedFolder = mutableStateOf<List<String>>(emptyList())

    fun selectFolder(folder: GalleryFolder) {
        selectedFolder.value = folder
        loadImagesForSelectedFolder(folder)
    }

    private fun loadImagesForSelectedFolder(folder: GalleryFolder) {
        coroutineScope.launch {
            val images = getImagesInFolder(folder.folderPath)
            imagesInSelectedFolder.value = images
        }
    }

    private suspend fun getImagesInFolder(folderPath: String): List<String> {
        val imagePaths = mutableListOf<String>()
        withContext(Dispatchers.IO) {
            val mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val selection = "${MediaStore.Images.Media.DATA} LIKE ?"
            val selectionArgs = arrayOf("$folderPath%")

            val cursor = contentResolver?.query(mediaUri, projection, selection, selectionArgs, null)
            cursor?.use {
                while (it.moveToNext()) {
                    val dataPath = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    imagePaths.add(dataPath)
                }
            }
        }
        return imagePaths
    }

    fun loadGalleryFolders() {
        coroutineScope.launch {
            val mediaFolderList = fetchGalleryFolders()
            galleryFolders.value = mediaFolderList
            if (mediaFolderList.isEmpty()) {
                selectedFolder.value = null
            }
        }
    }

    private suspend fun fetchGalleryFolders(): ArrayList<GalleryFolder> {
        val mediaFolders = ArrayList<GalleryFolder>()
        withContext(Dispatchers.IO) {
            val folderPaths = ArrayList<String>()
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

            val cursor = contentResolver?.query(mediaUri, projection, selection, selectionArgs, null)
            cursor?.use {
                while (it.moveToNext()) {
                    try {
                        val folderName = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                        val dataPath = it.getString(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                        var folderPath = dataPath.substring(0, dataPath.lastIndexOf("$folderName/"))
                        folderPath = "$folderPath$folderName/"
                        if (!folderPaths.contains(folderPath)) {
                            folderPaths.add(folderPath)
                            val galleryFolder = GalleryFolder(folderName, folderPath, dataPath)
                            galleryFolder.incrementImageCount()
                            mediaFolders.add(galleryFolder)
                        } else {
                            for (i in mediaFolders.indices) {
                                if (mediaFolders[i].folderPath == folderPath) {
                                    mediaFolders[i].firstImagePath = dataPath
                                    mediaFolders[i].incrementImageCount()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return mediaFolders
    }
}





