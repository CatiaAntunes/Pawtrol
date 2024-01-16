package com.example.pawtrolapp.android.post

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.components.CommentListItem
import com.example.pawtrolapp.android.common.components.ContactShareRow
import com.example.pawtrolapp.android.common.components.PostListItem
import com.example.pawtrolapp.android.common.theming.LargeSpacing
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostDetailScreen(
    postId: String,
    modifier: Modifier = Modifier,
){
    val viewModel: PostDetailScreenViewModel = viewModel()

    val postUiState by viewModel.postUiState
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val db = FirebaseFirestore.getInstance()
    fun submitComment(commentText: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { uid ->
            val comment = hashMapOf(
                "authorId" to uid,
                "text" to commentText,
                "date" to System.currentTimeMillis().toString()
            )
            db.collection("posts").document(postId)
                .collection("comments").add(comment)
                .addOnSuccessListener {
                    Log.d(TAG, "Comment added with ID: ${it.id}")
                    viewModel.fetchPostAndComments(postId)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding comment", e)
                }
        }
    }

    if (showDialog) {
        AddCommentDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onSubmitComment = { submitComment(it) }
        )
    }

    LaunchedEffect(key1 = Unit){
        viewModel.fetchPostAndComments(postId)
    }

    val post = postUiState.post

    if (postUiState.isLoading){
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }else if (postUiState.post != null){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ){
            item (key = "post_item"){
                if (post != null) {
                    PostListItem(
                        post = post,
                        onPostClick = {},
                        isDetailScreen = true
                    )
                }

                if (post != null) {
                    ContactShareRow(post = post)
                }


            }

            item {
                CommentSectionHeader(onAddCommentClicked = { showDialog = true })
            }

            itemsIndexed(items = postUiState.comments) { index, comment ->
                CommentListItem(comment = comment)
            }
        }
    }else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column (
                verticalArrangement = Arrangement.spacedBy(LargeSpacing)
            ) {
                Text(
                    text = stringResource(id = R.string.loading_post_error),
                    style = MaterialTheme.typography.bodySmall
                )

                OutlinedButton(onClick = { viewModel.fetchPostAndComments(postId)}) {
                    Text(text = stringResource(id = R.string.retry_button_label))
                }

            }
        }
    }


}

@Composable
fun CommentSectionHeader(
    modifier: Modifier = Modifier,
    onAddCommentClicked: () -> Unit
){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = stringResource(id = R.string.comment_header_label),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedButton(onClick = onAddCommentClicked) {
            Text(text = stringResource(id = R.string.new_comment_button_label))
        }
    }
}

@Composable
fun AddCommentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmitComment: (String) -> Unit
) {
    var commentText by rememberSaveable { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Add Comment") },
            text = {
                TextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    placeholder = { Text("Write a comment...") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(commentText.isNotBlank()) { // Avoid submitting empty comments
                            onSubmitComment(commentText)
                            commentText = "" // Clear the text field after submission
                        }
                        onDismiss()
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}


/*
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PostDetailPreview(){
    PawtrolTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            PostDetailScreen(
                postUiState = PostUiState(
                    isLoading = false,
                    post = samplePosts.first()
                ),
                onAddCommentClick = { */
/*TODO*//*
 },
                fetchData = {}
            )
        }
    }
}*/
