package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Languages : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_lang, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_language)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AdapterLang(getLanguagesList())

        return view
    }

    private fun getLanguagesList(): List<LanguageModel> {
        val languages: List<String> = listOf(
            getString(R.string.russian_lang),
            getString(R.string.english_lang),
            getString(R.string.chinese_lang),
            getString(R.string.belarus_lang),
            getString(R.string.kazakh_lang)
        )

        return languages.map { LanguageModel(it) }
    }
}