package com.example.myapplication.logic_with_interface

import android.content.Context

interface LogoutInterafce {
    fun clearOnBoardingFlag(context: Context)
    fun clearAuthFlag(context: Context)
    fun clearUserData(context: Context)
}