package com.example.pawtrolapp.android.common.fake_data

data class Post(
    var id: String = "",
    val text: String = "",
    val imageURL: String = "",
    val createdAt: String = "",
    val shareCount: Int = 0,
    val commentCount: Int = 0,
    val authorId: String = "",
    val authorName: String = "",
    val authorImage: String ="",
    val location: String = "",
    val situation: String = "",
    val contactInfo: String = "",
)


val samplePosts = listOf(
    Post(
        id = "11",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageURL = "https://i.ibb.co/0y6GR62/caramelo1.webp",
        createdAt = "20 min",
        shareCount = 12,
        commentCount = 3,
        authorId = "1",
        authorName = "Jane Doe",
        authorImage = "https://i.ibb.co/0y6GR62/caramelo1.webp",
        location = "Birmingham",
        situation = "Lost / Abandoned dog",
        contactInfo = "+351 919992324"
    ),
    Post(
        id = "12",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageURL = "https://picsum.photos/400",
        createdAt = "May 03, 2023",
        shareCount = 121,
        commentCount = 23,
        authorId = "2",
        authorName = "John Smith",
        authorImage = "https://picsum.photos/200",
        location = "Birmingham",
        situation = "Lost / Abandoned dog",
        contactInfo = "+351 912345632"
    ),
    Post(
        id = "13",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageURL = "https://picsum.photos/400",
        createdAt = "Apr 12, 2023",
        shareCount = 221,
        commentCount = 41,
        authorId = "3",
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        location = "Birmingham",
        situation = "Lost / Abandoned dog",
        contactInfo = "+351 9134562784"
    ),
    Post(
        id = "14",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageURL = "https://picsum.photos/400",
        createdAt = "Mar 31, 2023",
        shareCount = 90,
        commentCount = 13,
        authorId = "3",
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        location = "Birmingham",
        situation = "Lost / Abandoned dog",
        contactInfo = "+351 913456839"
    ),
    Post(
        id = "15",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageURL = "https://picsum.photos/400",
        createdAt = "Jan 30, 2023",
        shareCount = 121,
        commentCount = 31,
        authorId = "4",
        authorName = "L. James",
        authorImage = "https://picsum.photos/200",
        location = "Birmingham",
        situation = "Lost / Abandoned dog",
        contactInfo = "+351 912349706"
    ),
)