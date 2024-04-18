package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.three_screens_onboarding.Onb_1
import com.example.myapplication.three_screens_onboarding.Onb_2
import com.example.myapplication.three_screens_onboarding.Onb_3

class ViewPager : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf (
            Onb_1(),
            Onb_2(),
            Onb_3()
        )

        val adapter = AdapterVPager(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val vPager = view.findViewById<ViewPager2>(R.id.viewPager)
        vPager.adapter = adapter

        return view
    }
}