package com.example.myapplication.post_listing

data class PostListingResponse(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)
data class Data(
    val list: List<Post>,
    val totalCount: Int,
    val userTotalPostCount: Int
)

data class Post(
    val postId: Int,
    val canDisplay: Boolean,
    val image: String,
    val likeCount: Int,
    val price: Int,
    val status: String,
    val title: String,
    val transactionStatus: Any,
    val updatedAt: String
)