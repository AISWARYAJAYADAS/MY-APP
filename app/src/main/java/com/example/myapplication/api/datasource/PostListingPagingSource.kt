package com.example.myapplication.api.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.DotCApplication
import com.example.myapplication.R
import com.example.myapplication.api.model.Output
import com.example.myapplication.post_listing.Post
import com.example.myapplication.utils.INDEX.INDEX_0
import com.example.myapplication.utils.INDEX.INDEX_1
import com.example.myapplication.utils.INDEX.INDEX_2
import com.example.myapplication.utils.ListingStatus

class PostListingPagingSource(
    private val dataSource: RemoteDataSource,
    private val limit: Int,
    private val postStatus: String?,
    private val sortBy: String?,
    private val reviewPending: Boolean?,
    private val tabCounts: ArrayList<Int?>?
) : PagingSource<Int, Post>() {

    private var initialLoadSize = limit
    var existingDataCount = 0
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        var position = params.key ?: 0
        val loadSize = params.loadSize

        //initial load size is 3X limit. Set offset for first page.
        if (loadSize == limit * 3) {
            initialLoadSize = loadSize
            position = 0
            existingDataCount = 0
        } else if (loadSize == limit && existingDataCount == 0) {
            //Set offset for second page.
            position = 1
            existingDataCount = loadSize
        } else {
            //Set offset for subsequent pages.
            existingDataCount += limit
        }

        // Fetch data from remote data source
        val response = dataSource.getPostListing(
            limit = limit,
            existingDataCount = existingDataCount,
            postStatus = postStatus,
            sortBy = sortBy,
            reviewPending = reviewPending
        )

        return when (response) {
            is Output.Success -> {
                val data = response.value?.data
                val totalCount = data?.totalCount ?: 0
                val dataList = data?.list ?: emptyList()
                if ((tabCounts?.count() ?: 0) > 2) {
                    when (postStatus) {
                        ListingStatus.PUBLISHED -> {
                            tabCounts?.set(INDEX_0, totalCount)
                        }

                        ListingStatus.ORDER_PLACED -> {
                            tabCounts?.set(INDEX_1, totalCount)
                        }

                        ListingStatus.ORDER_COMPLETED -> {
                            tabCounts?.set(INDEX_2, totalCount)
                        }
                    }

                }

                LoadResult.Page(
                    data = dataList,
                    prevKey = null,
                    nextKey = if (dataList.isEmpty()) null else position + 1
                )
            }

            is Output.Error -> {
                if (response.apiError.statusMessage.isNullOrEmpty()) {
                    LoadResult.Error(throwable = Throwable(DotCApplication.getString(R.string.common_network_error)))
                } else {
                    LoadResult.Error(throwable = Throwable(response.apiError.statusMessage))
                }
            }
        }
    }
}