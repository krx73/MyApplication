package com.example.myapplication.logic_with_interface

import android.content.Context

class LogoutImpl : LogoutInterafce {

    override fun clearOnBoardingFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearAuthFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearUserData(context: Context) {
        val sharedPref = context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }
}