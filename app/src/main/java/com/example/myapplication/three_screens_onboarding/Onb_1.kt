package com.example.myapplication.three_screens_onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.UserFuns

class Onb_1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onb_1, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        val textViewSkip = view.findViewById<TextView>(R.id.first_skip_text_view)

        val userDataLogicImpl = UserFuns()

        btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        textViewSkip.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_languages)
        }
        return view
    }

}