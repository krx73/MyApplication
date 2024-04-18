package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class ScreenLogin : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_login, container, false)

        val loginButton = view.findViewById<Button>(R.id.btn_login)
        val signUpTextView = view.findViewById<TextView>(R.id.tView_signup)
        val emailEditText = view.findViewById<EditText>(R.id.editText_email_login)
        val passEditText = view.findViewById<EditText>(R.id.editText_pass_login)

        val userDataLogicImpl = UserFuns()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), EMAIL_PATTERN)) {
                    emailEditText.error = "Input valid email!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        passEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), PASSWORD_PATTERN)) {
                    passEditText.error = "Input valid password!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passEditText.text.toString()

            if (userDataLogicImpl.isValidData(
                    email,
                    EMAIL_PATTERN
                ) && userDataLogicImpl.isValidData(password, PASSWORD_PATTERN)
            ) {
                lifecycleScope.launch {
                    if (userDataLogicImpl.getExistedUser(email, password).isNotEmpty()) {
                        userDataLogicImpl.makeAuthSharedFlag(requireContext(), email, password)

                        findNavController().navigate(R.id.action_loginScreenFragment_to_homeFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Check your data or sign up.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Enter a valid data!", Toast.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_signUpScreen)
        }

        return view
    }
}