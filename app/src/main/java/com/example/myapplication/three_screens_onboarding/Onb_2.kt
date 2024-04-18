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

class Onb_2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onb_2, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val btnMore = view.findViewById<Button>(R.id.btn_more)
        val textViewSkip = view.findViewById<TextView>(R.id.second_skip_text_view)

        val userDataLogicImpl = UserFuns()

        btnMore.setOnClickListener {
            viewPager?.currentItem = 2
        }

        textViewSkip.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_languages)
        }

        return view
    }
}