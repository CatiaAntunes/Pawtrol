package com.example.pawtrolapp.android.createPost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pawtrolapp.android.common.fake_data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CreatePostViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val postCreationSuccess = MutableLiveData<Boolean>()

    fun createPost(text: String, location: String, contactNumber: String, situation: String, imageUrl: String) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {

            db.collection("users").document(currentUser.uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val username = documentSnapshot.getString("username")
                    val profileUrl = documentSnapshot.getString("profileUrl")

                    val newPost = Post(
                        text = text,
                        location = location,
                        contactInfo = contactNumber,
                        situation = situation,
                        imageURL = imageUrl,
                        createdAt = System.currentTimeMillis().toString(),
                        shareCount = 0,
                        commentCount = 0,
                        authorId = currentUser.uid,
                        authorName = username ?: "",
                        authorImage = profileUrl ?: ""
                    )
                    savePost(newPost)
                }
                .addOnFailureListener { e ->
                    Log.e("CreatePostViewModel", "Error fetching user details", e)
                }
        } else {

            postCreationSuccess.value = false
        }
    }

    fun uploadImage(imageUri: Uri, onUploadComplete: (Boolean, String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference.child("postImages/${imageUri.lastPathSegment}")
        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString()
                onUploadComplete(true, downloadUrl)
            } else {
                onUploadComplete(false, "")
            }
        }
    }
    private fun savePost(newPost: Post) {
        db.collection("posts").add(newPost)
            .addOnSuccessListener {
                Log.d("CreatePostViewModel", "Post created successfully")
                postCreationSuccess.value = true
            }
            .addOnFailureListener { e ->
                Log.e("CreatePostViewModel", "Error creating post", e)
                postCreationSuccess.value = false
            }
    }
}



