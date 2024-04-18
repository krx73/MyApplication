package com.example.myapplication.four_games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R

class Game_2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_2, container, false)

        val button_first_answer = view.findViewById<Button>(R.id.button_first_answer)
        val button_second_answer = view.findViewById<Button>(R.id.button_second_answer)
        val button_third_answer = view.findViewById<Button>(R.id.button_third_answer)
        val button_fourth_answer = view.findViewById<Button>(R.id.button_fourth_answer)
        val button_next = view.findViewById<Button>(R.id.button_next_answer)

        button_first_answer.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }
        button_second_answer.setOnClickListener {
            Toast.makeText(context, "That's right!", Toast.LENGTH_SHORT).show()
        }
        button_third_answer.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }
        button_fourth_answer.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }
        button_next.setOnClickListener {
            Toast.makeText(context, "All questions were shown!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}