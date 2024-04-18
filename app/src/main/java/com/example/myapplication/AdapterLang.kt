package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.logic_with_interface.OnItemClickListenerInterface

class AdapterLang(private val list: List<LanguageModel>) :
    RecyclerView.Adapter<AdapterLang.LanguageViewHolder>() {

    private var onItemClickListener: OnItemClickListenerInterface? = null

    class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewLanguage: TextView = itemView.findViewById(R.id.text_view_language)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return LanguageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.textViewLanguage.text = list[position].languageName
        holder.itemView.setOnClickListener {
            when (list[position].languageName) {
                "English", "Russian" -> {
                    Navigation.findNavController(holder.itemView).navigate(R.id.action_languages_to_loginScreenFragment)
                }
            }
        }
        onItemClickListener?.onItemClick(position)
    }
}