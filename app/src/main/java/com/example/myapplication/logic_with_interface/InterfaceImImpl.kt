package com.example.myapplication.logic_with_interface

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage


class InterfaceImImpl : InterfaceIm {

    override suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String {
        val supabaseClient = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Postgrest) }
        val supabaseStorage = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Storage) }
        val bucket = supabaseStorage.storage.from("myApp")

        val outputPath =
            "avatars/" + userEmail.hashCode().toString() + "_" + userImage.hashCode() + ".jpg"
        bucket.upload(outputPath, userImage!!, upsert = false)

        supabaseClient.from("myUsers").update(
            {
                set("userImage", outputPath)
            }
        ) {
            filter {
                eq("email", userEmail)
            }
        }
        return outputPath
    }

    override suspend fun getBitmapFromUri(imageUri: Uri): Bitmap {
        val supabaseStorage = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Storage) }
        val bucket = supabaseStorage.storage.from("myApp")
        val bytes = bucket.downloadAuthenticated(imageUri.toString())
        return BitmapFactory.decodeStream(bytes.inputStream())
    }
}