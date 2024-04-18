package com.example.myapplication.logic_with_interface

import android.graphics.Bitmap
import android.net.Uri

interface InterfaceIm {
    suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String
    suspend fun getBitmapFromUri(imageUri: Uri): Bitmap
}