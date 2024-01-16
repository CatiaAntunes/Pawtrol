package com.example.pawtrolapp.android.common.fake_data

data class User(
    val id: String = "",
    val username: String = "",
    val profileUrl: String = "https://picsum.photos/200",
    val isFollowing: Boolean = false
)

val sampleUsers = listOf(
    User(
        id = "1",
        username = "Mr Dip",
        profileUrl = "https://picsum.photos/200"
    ),
    User(
        id = "2",
        username = "John Cena",
        profileUrl = "https://picsum.photos/200"
    ),
    User(
        id = "3",
        username = "Cristiano",
        profileUrl = "https://picsum.photos/200"
    ),
    User(
        id = "4",
        username = "L. James",
        profileUrl = "https://picsum.photos/200"
    )
)