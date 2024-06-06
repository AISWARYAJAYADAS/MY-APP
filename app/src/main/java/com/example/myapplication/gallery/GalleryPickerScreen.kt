package com.example.myapplication.gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.components.CustomAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryPickerScreen(navHostController: NavHostController) {
    val galleryPickerViewModel = hiltViewModel<GalleryPickerViewModel>()
    val context = LocalContext.current

    var hasPermissions by remember { mutableStateOf(false) }
    var showPermissionDeniedMessage by remember { mutableStateOf(false) }

    val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionsGranted = permissions.values.all { it }
        if (allPermissionsGranted) {
            hasPermissions = true
            Log.d("GalleryPickerScreen", "All required permissions granted")
            galleryPickerViewModel.getGalleryFolders()
        } else {
            showPermissionDeniedMessage = true
            Log.d("GalleryPickerScreen", "Permission denied")
        }
    }

    LaunchedEffect(key1 = true) {
        permissionsLauncher.launch(requiredPermissions)
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = R.string.create_listing_title_gallery,
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                },
                actions = {
                    Row {
                        Text(text = stringResource(id = R.string.next), fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(galleryPickerViewModel.galleryClickedItem.value)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_profile_image_placeholder),
                error = painterResource(id = R.drawable.ic_profile_image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(365.dp)
            )

            if (hasPermissions) {
                //  GalleryFolderList(galleryFolders = galleryPickerViewModel.galleryFolders.value)
                //GalleryFolderDropdown(galleryFolders = galleryPickerViewModel.galleryFolders.value)
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GalleryFolderDropdown(
                        galleryFolders = galleryPickerViewModel.galleryFolders.value,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera_gallery),
                        contentDescription = "Camera",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { /* Handle camera icon click */ }
                    )
                }
            } else {
                if (showPermissionDeniedMessage) {
                    PermissionDeniedMessage(context)
                } else {
                    Text(text = "Requesting permissions...")
                }
            }
        }
    }
}


@Composable
fun PermissionDeniedMessage(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Permission required to display gallery folders")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }) {
            Text(text = "Open Settings")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryFolderList(galleryFolders: List<GalleryFolder>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(galleryFolders) { galleryFolder ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(color = Color.LightGray)
            ) {
                Text(
                    text = galleryFolder.name,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryFolderDropdown(galleryFolders: List<GalleryFolder>, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf<GalleryFolder?>(null) }
    val labelText = selectedFolder?.name ?: galleryFolders.firstOrNull()?.name ?: "Select Folder"

    Box(
        modifier = modifier
            .clickable { expanded = !expanded }
            .padding(8.dp) // Padding to make it easier to click
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = labelText)
            (if (expanded) null else R.drawable.ic_arrow_down)?.let { painterResource(id = it) }?.let {
                Icon(
                    painter = it,
                    contentDescription = null
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            galleryFolders.forEach { folder ->
                DropdownMenuItem(
                    text = { Text(folder.name) },
                    onClick = {
                        selectedFolder = folder
                        expanded = false
                    }
                )
            }
        }
    }
}

