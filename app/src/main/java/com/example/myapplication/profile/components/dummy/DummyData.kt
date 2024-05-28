package com.example.myapplication.profile.components.dummy


// Data class representing a Post
data class Post(
    val title: String,
    val price: String,
    val image: String,
    val status: String? = null, // Status can be null if not applicable
    val transactionStatus: String? = null // Transaction status can be null if not applicable
)

// Simulated data fetching functions
fun fetchPublishedListings(): List<Post> {
    return listOf(
        Post(title = "Item 1", price = "$100", image = "image1.jpg", status = "Published"),
        Post(title = "Item 2", price = "$200", image = "image2.jpg", status = "Published"),
        Post(title = "Item 7", price = "$700", image = "image7.jpg", status = "Published"),
        Post(title = "Item 8", price = "$800", image = "image8.jpg", status = "Published"),
        Post(title = "Item 9", price = "$900", image = "image9.jpg", status = "Published")
        // Add more items if needed
    )
}

fun fetchOrderPlacedListings(): List<Post> {
    return listOf(
        Post(title = "Item 3", price = "$300", image = "image3.jpg", transactionStatus = "Order Placed"),
        Post(title = "Item 4", price = "$400", image = "image4.jpg", transactionStatus = "Order Placed"),
        Post(title = "Item 10", price = "$1000", image = "image10.jpg", transactionStatus = "Order Placed"),
        Post(title = "Item 11", price = "$1100", image = "image11.jpg", transactionStatus = "Order Placed")
        // Add more items if needed
    )
}

fun fetchOrderCompletedListings(): List<Post> {
    return listOf(
        Post(title = "Item 5", price = "$500", image = "image5.jpg", transactionStatus = "Order Completed"),
        Post(title = "Item 6", price = "$600", image = "image6.jpg", transactionStatus = "Order Completed"),
        Post(title = "Item 12", price = "$1200", image = "image12.jpg", transactionStatus = "Order Completed"),
        Post(title = "Item 13", price = "$1300", image = "image13.jpg", transactionStatus = "Order Completed")
        // Add more items if needed
    )
}
