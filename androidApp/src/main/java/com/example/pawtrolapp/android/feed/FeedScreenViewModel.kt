package com.example.pawtrolapp.android.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtrolapp.android.common.fake_data.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class FeedScreenViewModel : ViewModel() {
    var postsUiState by mutableStateOf(PostsUiState())
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        postsUiState = postsUiState.copy(isLoading = true)
        val db = FirebaseFirestore.getInstance()

        viewModelScope.launch {
            db.collection("posts")
                .get()
                .addOnSuccessListener { documents ->
                    val postsList = mutableListOf<Post>()
                    for (document in documents) {
                        val post = document.toObject(Post::class.java).apply {
                            id = document.id
                        }
                        postsList.add(post)
                    }
                    postsUiState = postsUiState.copy(
                        isLoading = false,
                        posts = postsList
                    )
                }
                .addOnFailureListener { exception ->
                    postsUiState = postsUiState.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
        }
    }
}

data class PostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val errorMessage: String? = null
)
