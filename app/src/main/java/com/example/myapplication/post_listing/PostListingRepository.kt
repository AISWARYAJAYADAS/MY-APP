package com.example.myapplication.post_listing

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.api.datasource.PostListingPagingSource
import com.example.myapplication.api.datasource.RemoteDataSource
import javax.inject.Inject

class PostListingRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    // Fetches paginated post listing data from the API
    fun getPostListing(
        limit: Int,
        postStatus: String?,
        sortBy: String?,
        reviewPending: Boolean? = null,
        tabCounts: ArrayList<Int?>? = null
    ) = Pager(
        config = PagingConfig(
            pageSize = limit,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            PostListingPagingSource(
                dataSource = remoteDataSource,
                limit = limit,
                postStatus = postStatus,
                sortBy = sortBy,
                reviewPending = reviewPending,
                tabCounts = tabCounts
            )

        }

    ).flow
}