package com.example.myapplication

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.logic_with_interface.AUTHORIZED_STATE_KEY
import com.example.myapplication.logic_with_interface.EMAIL_KEY
import com.example.myapplication.logic_with_interface.FINISHED_STATE_KEY
import com.example.myapplication.logic_with_interface.PASSWORD_KEY
import com.example.myapplication.logic_with_interface.PREF_AUTH_NAME
import com.example.myapplication.logic_with_interface.PREF_COMPLETE_NAME
import com.example.myapplication.logic_with_interface.PROJECT_URL
import com.example.myapplication.logic_with_interface.SUPABASE_KEY
import com.example.myapplication.logic_with_interface.USER_DATA_KEY
import com.example.myapplication.logic_with_interface.USER_EMAIL
import com.example.myapplication.logic_with_interface.USER_FIRST_NAME
import com.example.myapplication.logic_with_interface.USER_IMAGE
import com.example.myapplication.logic_with_interface.USER_LAST_NAME
import com.example.myapplication.logic_with_interface.UserDataInterface
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

class UserFuns : UserDataInterface {
    override suspend fun getExistedUser(email: String, password: String): List<UserModel> =
        withContext(Dispatchers.IO) {
            val supabaseClient = createSupabaseClient(
                supabaseUrl = PROJECT_URL,
                supabaseKey = SUPABASE_KEY
            ) { install(Postgrest) }

            val existUser: List<UserModel> = async {
                supabaseClient.from("myUsers")
                    .select(
                        columns = Columns.list(
                            "firstname",
                            "lastname",
                            "userImage",
                            "email",
                            "password",
                            "score"
                        )
                    ) {
                        filter {
                            eq("email", email)
                            eq("password", hashedPass(password))
                        }
                    }.decodeList<UserModel>()
            }.await()

            existUser
        }

    override fun registerNewUser(userModel: UserModel) {
        val supabaseClient = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
        }
        GlobalScope.launch {
            supabaseClient.from("myUsers").insert(userModel)
        }
    }

    override fun onBoardingCompleted(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putBoolean(FINISHED_STATE_KEY, true)
            .apply()
    }

    override fun isOnBoardingCompleted(requireActivity: FragmentActivity): Boolean {
        val sharedPref =
            requireActivity.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(FINISHED_STATE_KEY, false)
    }

    override fun makeAuthSharedFlag(context: Context, email: String, password: String) {
        val sharedPref = context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putBoolean(AUTHORIZED_STATE_KEY, true)
            .putString(EMAIL_KEY, email)
            .putString(PASSWORD_KEY, password)
            .apply()
    }

    override fun getAuthorizedEmailPass(requireActivity: FragmentActivity): List<String?> {
        val sharedPref = requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)

        return listOf(
            sharedPref.getString(EMAIL_KEY, ""), sharedPref.getString(PASSWORD_KEY, "")
        )
    }

    override fun isAnyoneAuthorized(requireActivity: FragmentActivity): Boolean {
        val sharedPref = requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(AUTHORIZED_STATE_KEY, false)
    }

    override fun putDataUserProfileScreen(userModel: UserModel, context: Context) {
        val sharedPref = context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putString(USER_FIRST_NAME, userModel.firstname)
            .putString(USER_LAST_NAME, userModel.lastname)
            .putString(USER_EMAIL, userModel.email)
            .putString(USER_IMAGE, userModel.userImage)
            .apply()
    }

    override fun getDataUserProfileScreen(requireActivity: FragmentActivity): UserModel {
        requireActivity.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE).apply {
            return UserModel(
                firstname = getString(USER_FIRST_NAME, "")!!,
                lastname = getString(USER_LAST_NAME, "")!!,
                email = getString(USER_EMAIL, "")!!,
                userImage = getString(USER_IMAGE, "")!!
            )
        }
    }


    override fun isValidData(data: String, needPattern: String): Boolean {
        val pattern = Pattern.compile(needPattern)
        return pattern.matcher(data).matches() && data.isNotEmpty()
    }

    override fun isEverythingOkay(userData: UserModel): Boolean {
        return isValidData(userData.firstname, USERNAME_PATTERN) &&
                isValidData(userData.lastname, USERNAME_PATTERN) &&
                isValidData(userData.email, EMAIL_PATTERN)
    }

    override fun hashedPass(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    override suspend fun getTopUsers(): List<UserModel> =
        withContext(Dispatchers.IO) {
            val supabaseClient = createSupabaseClient(
                supabaseUrl = PROJECT_URL,
                supabaseKey = SUPABASE_KEY
            ) { install(Postgrest) }

            val userData: List<UserModel> = async {
                supabaseClient.from("myUsers")
                    .select(
                        columns = Columns.list(
                            "firstname",
                            "lastname",
                            "userImage",
                            "email",
                            "password",
                            "score"
                        )
                    )
                    .decodeList<UserModel>()
            }.await()

            userData.sortedByDescending { it.score }.take(3)
        }

}