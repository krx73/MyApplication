package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class SignUpPassword : Fragment() {
    private val viewModel: VMUser by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up_password, container, false)

        val textViewSignIn = view.findViewById<TextView>(R.id.tView_signIn_third)
        val buttonSignUp = view.findViewById<Button>(R.id.btn_complete_signUp)
        val editTextPass = view.findViewById<EditText>(R.id.editText_pass)
        val editTextConfirmedPass = view.findViewById<EditText>(R.id.editText_confirm_pass)
        val userDataLogicImpl = UserFuns()

        textViewSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPassword_to_loginScreenFragment)
        }

        editTextPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), PASSWORD_PATTERN)) {
                    editTextPass.error = "Incorrect password format!"
                } else {
                    editTextPass.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        buttonSignUp.setOnClickListener {
            val password = editTextPass.text.toString()
            val confirmedPassword = editTextConfirmedPass.text.toString()

            if (userDataLogicImpl.isValidData(password, PASSWORD_PATTERN)) {
                if (password == confirmedPassword) {
                    val userWithPassword =
                        viewModel.combineWithPassword(userDataLogicImpl.hashedPass(password))
                    if (userWithPassword != null) {
                        userDataLogicImpl.registerNewUser(userWithPassword)

                        findNavController().navigate(R.id.action_signUpPassword_to_loginScreenFragment)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Passwords aren't the same!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Password should contain at least 8 characters!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }
}