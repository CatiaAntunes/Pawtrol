package com.example.pawtrolapp.android.createPost

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pawtrolapp.android.destinations.FeedDestination
import com.example.pawtrolapp.android.feed.FeedScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun CreatePost(
    navigator: DestinationsNavigator
) {
    val viewModel: CreatePostViewModel = koinViewModel()
    val postCreationSuccess by viewModel.postCreationSuccess.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    if (postCreationSuccess == true) {
        showDialog = true
        //viewModel.resetPostCreationSuccess() // Reset the flag in ViewModel
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Post Created") },
            text = { Text(text = "Your post was successfully created.") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    navigator.navigate(FeedDestination)
                }) {
                    Text("OK")
                }
            }
        )
    }

    CreatePostScreen(viewModel = viewModel)
}