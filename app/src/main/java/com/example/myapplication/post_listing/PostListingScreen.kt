package com.example.myapplication.post_listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.components.CustomTabLayout
import com.example.myapplication.utils.ListingSortType
import kotlinx.coroutines.flow.Flow


@Composable
fun PostListingScreen(viewModel: PostListingViewModel = hiltViewModel()) {
    val tabs = listOf("Published", "Order Placed", "Order Completed")
    val selectedTabIndex by remember { derivedStateOf { viewModel.selectedTabPosition } }
    val selectedSortType by viewModel.selectedSortType.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Sorting options
        SortOptions(
            selectedSortType = selectedSortType,
            onSortSelected = { sortType -> viewModel.onSortTypeSelected(sortType) }
        )

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { viewModel.onTabSelected(index) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PostList(postsFlow = viewModel.getPublishedListings())
            1 -> PostList(postsFlow = viewModel.getOrderPlacedListings())
            2 -> PostList(postsFlow = viewModel.getOrderCompletedListings())
        }
    }
}



@Composable
fun SortOptions(selectedSortType: String, onSortSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Button(onClick = { expanded = !expanded }) {
                Text(text = selectedSortType)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortSelected(ListingSortType.NEWEST)
                }, text = {
                    Text("Newest")
                })
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortSelected(ListingSortType.MOST_LIKED)
                }, text = {
                    Text("Most Liked")
                })
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortSelected(ListingSortType.OLDEST)
                }, text = {
                    Text("Oldest")
                })
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortSelected(ListingSortType.PV_COUNT)
                }, text = {
                    Text("Most Viewed")
                })
            }
        }
    }
}



@Composable
fun PostList(postsFlow: Flow<PagingData<Post>>) {
    val posts = postsFlow.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(posts.itemCount) { index ->
            PostItem(post = posts[index])
        }
    }


}

@Composable
fun PostItem(post: Post?) {
    post?.let {
        Box(
            modifier = Modifier
                .padding(4.dp) // Adjust padding as needed
                .fillMaxWidth()
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .width(110.dp)
                        .height(146.dp),
                    model = it.image,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_profile_image_placeholder),
                    error = painterResource(id = R.drawable.ic_profile_image_placeholder),
                    contentDescription = "Post image",
                )
            }
        }
    }
}
