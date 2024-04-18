package com.example.myapplication.three_screens_onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.UserFuns

class Onb_3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onb_3, container, false)
        val btnFinish = view.findViewById<Button>(R.id.btn_choose_lang)
        val textViewSkip = view.findViewById<TextView>(R.id.third_skip_text_view)
        val userDataLogicImpl = UserFuns()

        btnFinish.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_languages)
        }
        textViewSkip.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_languages)
        }

        return view
    }
}