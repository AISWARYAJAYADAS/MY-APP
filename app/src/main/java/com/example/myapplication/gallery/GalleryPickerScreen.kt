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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.components.CustomAppBar
import com.example.myapplication.components.GalleryFolderDropdownTextField
import com.example.myapplication.graphs.CreateListingRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryPickerScreen(navHostController: NavHostController) {
    val galleryPickerViewModel = hiltViewModel<GalleryPickerViewModel>()
    val context = LocalContext.current

    fun openMediaStore() {
        galleryPickerViewModel.loadGalleryFolders()
        galleryPickerViewModel.selectedImagePaths.value = emptyList()
    }

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
            openMediaStore()
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
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navHostController.popBackStack()
                            }
                    )
                },
                actions = {
                    // Check if any image is selected
                    val isImageSelected =
                        galleryPickerViewModel.selectedImagePaths.value.isNotEmpty()

                    // Check if any image is selected to enable the row clickable behavior
                    if (isImageSelected) {
                        Row(
                            modifier = Modifier.clickable {
                                // Handle click action for "Next" here
                                navHostController.navigate(route = CreateListingRoutes.ListNewItemScreen.route)
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.next),
                                fontSize = 14.sp,
                                color = Color.Black,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_next),
                                contentDescription = null,
                                tint = Color.Black,
                            )
                        }
                    } else {
                        // Row is not clickable when no image is selected
                        Row {
                            Text(
                                text = stringResource(id = R.string.next),
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_next),
                                contentDescription = null,
                                tint = Color.Gray,
                            )
                        }
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
            if (hasPermissions) {
                Log.d(
                    "GalleryPickerScreen",
                    "Preview image URL: ${galleryPickerViewModel.previewImageUrl.value}"
                )

                AsyncImage(
                    model = galleryPickerViewModel.previewImageUrl.value,
                    placeholder = painterResource(R.drawable.ic_profile_image_placeholder),
                    error = painterResource(id = R.drawable.ic_profile_image_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(365.dp)
                )
                GalleryContent(galleryPickerViewModel)
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
fun GalleryContent(viewModel: GalleryPickerViewModel) {
    LaunchedEffect(viewModel.galleryFolders.value) {
        val folders = viewModel.galleryFolders.value
        if (folders.isNotEmpty() && viewModel.selectedFolder.value == null) {
            viewModel.selectFolder(folders.first())
        }
    }

    Column {
        GalleryDropdownAndCameraRow(viewModel)
        ImageGrid(viewModel)
    }
}

@Composable
private fun ImageGrid(galleryPickerViewModel: GalleryPickerViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(galleryPickerViewModel.imagesInSelectedFolder.value) { imagePath ->
            val isSelected = galleryPickerViewModel.selectedImagePaths.value.contains(imagePath)
            val selectedIndex =
                galleryPickerViewModel.selectedImagePaths.value.indexOf(imagePath) + 1
            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        galleryPickerViewModel.toggleImageSelection(imagePath)
                    }
            ) {
                Box {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x66000000)) // Semi-transparent overlay
                        ) {
                            // Round shape with text count
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(24.dp)
                                    .background(
                                        color = colorResource(id = R.color.blue),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = selectedIndex.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun GalleryDropdownAndCameraRow(viewModel: GalleryPickerViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GalleryFolderDropdown(
            galleryFolders = viewModel.galleryFolders.value,
            selectedFolder = viewModel.selectedFolder.value,
            onFolderSelected = { folder ->
                viewModel.selectFolder(folder)
            },
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
fun GalleryFolderDropdown(
    galleryFolders: List<GalleryFolder>,
    selectedFolder: GalleryFolder?, // Selected folder
    onFolderSelected: (GalleryFolder) -> Unit, // Callback when a folder is selected
    modifier: Modifier = Modifier
) {
    var isExpended by remember { mutableStateOf(false) }
    val selectedText = selectedFolder?.name ?: galleryFolders.firstOrNull()?.name ?: "Select Folder"

    ExposedDropdownMenuBox(
        expanded = isExpended,
        onExpandedChange = { isExpended = it },
    ) {

        GalleryFolderDropdownTextField(
            text = selectedText,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpended) },
            modifier = modifier
                .defaultMinSize(130.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpended,
            onDismissRequest = {
                isExpended = false
            },
            modifier = modifier
                .background(Color.White)
        ) {

            galleryFolders.forEachIndexed { index, galleryFolder ->
                Card(
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    modifier = Modifier
                        .padding(6.dp)
                        .clickable {
                            onFolderSelected(galleryFolder) // Call the callback to update the selected folder
                            isExpended = false
                        }
                ) {
                    Text(
                        text = galleryFolder.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(6.dp)

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (index < galleryFolders.size - 1) {
                        Divider()
                    }
                }
            }
        }
    }
}


