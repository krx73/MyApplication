package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

@SuppressLint("CustomSplashScreen")
class SplashS : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userDataLogicImpl = UserFuns()

        Handler().postDelayed({
            if (userDataLogicImpl.isOnBoardingCompleted(requireActivity())) {
                if (userDataLogicImpl.isAnyoneAuthorized(requireActivity())) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginScreenFragment)
                }
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
        }, 2000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}