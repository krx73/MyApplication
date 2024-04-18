package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.logic_with_interface.InterfaceImImpl
import com.example.myapplication.logic_with_interface.LogoutImpl
import kotlinx.coroutines.launch

class SettingsUser : Fragment() {

    private lateinit var imageViewAvatar: ImageView
    private lateinit var buttonChangeAvatar: Button
    private lateinit var userData: UserModel
    private val userImImpl = InterfaceImImpl()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val userDataLogicImpl = UserFuns()
        val textViewUsername = view.findViewById<TextView>(R.id.textView_username)
        val buttonLogOut = view.findViewById<Button>(R.id.button_logout)

        val userImageImpl = InterfaceImImpl()

        userData = userDataLogicImpl.getDataUserProfileScreen(requireActivity())

        imageViewAvatar = view.findViewById(R.id.imageView_avatar)
        buttonChangeAvatar = view.findViewById(R.id.button_change_avatar)

        textViewUsername.text = "${userData.firstname} ${userData.lastname}"
        if (userData.userImage.isNotEmpty()) {
            lifecycleScope.launch {
                val userImageBitmap = userImageImpl.getBitmapFromUri(userData.userImage.toUri())
                imageViewAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                imageViewAvatar.setImageBitmap(userImageBitmap)
            }

        }

        buttonChangeAvatar.setOnClickListener {
            pickImageFromGallery()
        }

        buttonLogOut.setOnClickListener {
            val logoutMethodsImpl = LogoutImpl()

            logoutMethodsImpl.clearOnBoardingFlag(requireContext())
            logoutMethodsImpl.clearAuthFlag(requireContext())
            logoutMethodsImpl.clearUserData(requireContext())

            findNavController().navigate(R.id.action_userProfileFragment_to_loginScreenFragment)
        }

        return view
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri = result.data?.data
                val inputStream = context?.contentResolver?.openInputStream(imageUri!!)
                val byteArray = inputStream?.readBytes()
                lifecycleScope.launch {
                    val outputPath = userImImpl.loadImageUriToSupabase(
                        byteArray,
                        userData.email
                    )
                    val userImageBitmap = userImImpl.getBitmapFromUri(outputPath.toUri())
                    imageViewAvatar.setImageBitmap(userImageBitmap)
                }
            }
        }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/jpeg"
        launcher.launch(intent)
    }
}