package com.example.pawtrolapp.android.common.components

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pawtrolapp.android.R
import com.example.pawtrolapp.android.common.fake_data.Comment
import com.example.pawtrolapp.android.common.fake_data.User
import com.example.pawtrolapp.android.common.theming.LargeSpacing
import com.example.pawtrolapp.android.common.theming.MediumSpacing
import com.example.pawtrolapp.android.common.theming.isLight
import com.google.firebase.firestore.FirebaseFirestore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentListItem(
    modifier: Modifier = Modifier,
    comment: Comment,
){
    val db = FirebaseFirestore.getInstance()
    var user by remember(comment.authorId) { mutableStateOf<User?>(null) }

    LaunchedEffect(comment.authorId) {
        comment.authorId?.let { authorId ->
            db.collection("users").document(authorId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val userData = documentSnapshot.toObject(User::class.java)
                    user = userData
                }
                .addOnFailureListener{
                    exception ->
                    Log.d(TAG, "Error fetching data from firebase")
                }
        }
    }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ){
        CircleImage(
            modifier = modifier.size(30.dp),
            url = user?.profileUrl ?: ""
        )
        
        Column (
            modifier = modifier
                .weight(1f)
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
            ){
                Text(
                    text = user?.username ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.alignByBaseline()

                )
                
                Text(
                    text = formatCreatedAt(comment.date.toLong()), //formatCreatedAt(comment.date.toLong())
                    style = MaterialTheme.typography.bodySmall,
                    color = if (MaterialTheme.colorScheme.isLight()){
                        Color.LightGray
                    } else {
                        Color.DarkGray
                    },
                    modifier = modifier
                        .alignByBaseline()
                        .weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.round_more_horiz_24),
                    contentDescription = null,
                    tint = if (MaterialTheme.colorScheme.isLight()){
                        Color.LightGray
                    } else {
                        Color.DarkGray
                    },
                    modifier = modifier
                )
            }
            
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}



