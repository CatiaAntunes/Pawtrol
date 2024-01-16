package com.example.pawtrolapp.android.createPost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.components.CustomTextField
import com.example.pawtrolapp.android.common.components.SituationDropdown
import com.example.pawtrolapp.android.common.theming.ButtonHeight
import com.example.pawtrolapp.android.common.theming.LightColors
import com.example.pawtrolapp.android.common.theming.MediumSpacing

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    var text by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    val situations = listOf("Lost / Abandoned", "Lost Pet", "Other")
    var selectedSituation by remember { mutableStateOf(situations[0]) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") }
    var isImageUploading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data
            imageUri?.let { uri ->
                isImageUploading = true
                viewModel.uploadImage(uri) { uploadSuccess, downloadUrl ->
                    isImageUploading = false
                    if (uploadSuccess) {
                        imageUrl = downloadUrl // Save the download URL
                    } else {
                        imageUri = null // Clear the image uri if upload failed
                    }
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = if (isDarkTheme) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surface)
                .padding(MediumSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CustomTextField(
                modifier = modifier
                    .fillMaxWidth(fraction = 0.9f),
                value = text,
                onValueChange = {newText ->
                    text = newText
                },
                hint = R.string.write_post,
            )
            Spacer(Modifier.height(8.dp))
            CustomTextField(
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth(fraction = 0.9f),
                value = location,
                onValueChange = {newText ->
                    location = newText
                },
                hint = R.string.location_post,
            )
            Spacer(Modifier.height(8.dp))
            SituationDropdown(
                situations = situations,
                selectedSituation = selectedSituation,
                onSituationSelect = { newSituation ->
                    selectedSituation = newSituation
                },
                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
            )
            Spacer(Modifier.height(8.dp))
            CustomTextField(
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth(fraction = 0.9f),
                value = contactNumber,
                onValueChange = {newText ->
                    contactNumber = newText
                },
                hint = R.string.phone_post,
            )
            Spacer(Modifier.height(16.dp))

            val context = LocalContext.current
            Button(
                onClick = {
                    if (imageUri == null) {
                        // Pick image
                        val pickImageIntent = Intent(Intent.ACTION_PICK)
                        pickImageIntent.type = "image/*"
                        imagePickerLauncher.launch(pickImageIntent)
                    } else {
                        // Upload image
                        imageUri?.let { uri ->
                            isImageUploading = true
                            viewModel.uploadImage(uri) { success, url ->
                                isImageUploading = false
                                if (success) {
                                    imageUrl = url // Save the URL for the post
                                } else {
                                    imageUri = null // Clear the URI if upload failed
                                }
                            }
                        }
                    }
                },
                enabled = !isImageUploading,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(ButtonHeight),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = LightColors.secondary)
            ) {
                Text(if (isImageUploading) "Uploading..." else "Upload Image")
            }
            Spacer(Modifier.height(16.dp))
            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected image preview",
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.createPost(text, location, contactNumber, selectedSituation, imageUrl)
                },
                enabled = imageUrl.isNotBlank() && !isImageUploading,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(ButtonHeight),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = LightColors.primary)
            ) {
                Text(text = "Create Post")
            }
            /*Spacer(Modifier.height(16.dp))
            Button(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(ButtonHeight),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = LightColors.primary)
            ) {
                Text(text = "Cancel")
            }*/
        }
    }



}

/*
@Preview
@Composable
fun PreviewCreatePostScreen(){
    val dummyViewModel = CreatePostViewModel()
    CreatePostScreen(
        viewModel = dummyViewModel,
        onPostSuccess = { },
        modifier = Modifier.padding(16.dp)
    )
}*/
