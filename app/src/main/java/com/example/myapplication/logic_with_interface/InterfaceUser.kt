package com.example.myapplication.logic_with_interface

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.UserModel

const val PREF_COMPLETE_NAME = "onboarding_complete"
const val FINISHED_STATE_KEY = "finished_state"

const val PREF_AUTH_NAME = "auth_completed"
const val AUTHORIZED_STATE_KEY = "is_authorized"
const val EMAIL_KEY = "authorized_email"
const val PASSWORD_KEY = "authorized_pass"

const val USER_DATA_KEY = "user_data"
const val USER_FIRST_NAME = "user_first_name"
const val USER_LAST_NAME = "user_last_name"
const val USER_EMAIL = "user_email"
const val USER_IMAGE = "user_image"

const val SUPABASE_KEY =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJ1bnFlY3VueW1oeHBhcGVxYmdrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTI0MTM0MTEsImV4cCI6MjAyNzk4OTQxMX0.BTMi2LxZtSp6aFKVQE3lDT_cmsTHTRUys8OXlfvzQqo"
const val PROJECT_URL = "https://bunqecunymhxpapeqbgk.supabase.co"

interface UserDataInterface {
    suspend fun getExistedUser(email: String, password: String = ""): List<UserModel>

    fun isValidData(data: String, needPattern: String): Boolean
    fun isEverythingOkay(userData: UserModel): Boolean

    fun onBoardingCompleted(context: Context)

    fun makeAuthSharedFlag(context: Context, email: String, password: String)
    fun getAuthorizedEmailPass(requireActivity: FragmentActivity): List<String?>
    fun registerNewUser(userModel: UserModel)

    fun putDataUserProfileScreen(userModel: UserModel, context: Context)
    fun getDataUserProfileScreen(requireActivity: FragmentActivity): UserModel

    fun isOnBoardingCompleted(requireActivity: FragmentActivity): Boolean
    fun isAnyoneAuthorized(requireActivity: FragmentActivity): Boolean

    fun hashedPass(input: String): String

    suspend fun getTopUsers(): List<UserModel>
}