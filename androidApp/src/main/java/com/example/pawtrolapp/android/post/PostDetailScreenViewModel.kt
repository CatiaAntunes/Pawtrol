package com.example.pawtrolapp.android.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pawtrolapp.android.common.fake_data.Comment
import com.example.pawtrolapp.android.common.fake_data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class PostDetailScreenViewModel: ViewModel() {
    private val _postUiState = mutableStateOf(PostUiState())
    val postUiState: State<PostUiState> = _postUiState

    fun fetchPostAndComments(postId: String) {
        _postUiState.value = _postUiState.value.copy(isLoading = true)
        val db = FirebaseFirestore.getInstance()

        // Fetch the post details
        db.collection("posts").document(postId).get()
            .addOnSuccessListener { documentSnapshot ->
                val post = documentSnapshot.toObject(Post::class.java)?.copy(id = documentSnapshot.id)
                post?.let {
                    db.collection("posts").document(postId)
                        .collection("comments")
                        .orderBy("date", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener { commentsSnapshot ->
                            val comments = commentsSnapshot.documents.mapNotNull { it.toObject(Comment::class.java) }
                            _postUiState.value = _postUiState.value.copy(
                                isLoading = false,
                                post = post,
                                comments = comments
                            )
                        }
                        .addOnFailureListener { e ->
                            _postUiState.value = _postUiState.value.copy(
                                isLoading = false,
                                errorMessage = e.message
                            )
                        }
                }
            }
            .addOnFailureListener { e ->
                _postUiState.value = _postUiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }

    }

}




data class PostUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val errorMessage: String? = null
)

