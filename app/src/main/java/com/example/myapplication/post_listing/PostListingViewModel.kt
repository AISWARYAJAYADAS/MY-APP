package com.example.myapplication.post_listing

import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostListingViewModel @Inject constructor() : ViewModel() {
    var selectedTabPosition = 0 // Fixed typo and used 0 instead of INDEX.INDEX_0
    var tabCounts = arrayListOf<Int?>(null, null, null)
}