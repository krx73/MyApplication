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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

const val PASSWORD_PATTERN = "^(?=\\S+$).{8,20}$"
const val EMAIL_PATTERN = "[a-z0-9._-]+@[a-z]+\\.+[a-z]+"
const val USERNAME_PATTERN = "^[a-zA-Z0-9_-]+$"

class SignUpScreen : Fragment() {
    private val viewModel: VMUser by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_screen, container, false)

        val firstNameEditText = view.findViewById<EditText>(R.id.editText_firstname)
        val lastNameEditText = view.findViewById<EditText>(R.id.editText_lastname)
        val emailEditText = view.findViewById<EditText>(R.id.editText_email)
        val signInTextView = view.findViewById<TextView>(R.id.tView_signIn)
        val continueButton = view.findViewById<Button>(R.id.btn_continue_signUp)

        val userDataLogicImpl = UserFuns()
        signInTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signUpScreen_to_loginScreenFragment)
        }

        firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), USERNAME_PATTERN)) {
                    firstNameEditText.error = "Incorrect format of firstname!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), USERNAME_PATTERN)) {
                    lastNameEditText.error = "Incorrect format of lastname!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), EMAIL_PATTERN)) {
                    emailEditText.error = "Incorrect email format!"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        continueButton.setOnClickListener {
            val firstNameData = firstNameEditText.text.toString()
            val lastNameData = lastNameEditText.text.toString()
            val emailData = emailEditText.text.toString()

            val userModelModel = UserModel(
                firstname = firstNameData,
                lastname = lastNameData,
                email = emailData
            )
            if (userDataLogicImpl.isEverythingOkay(userModelModel)) {
                viewModel.setRegistrationData(userModelModel)
                findNavController().navigate(R.id.action_signUpScreen_to_signUpPassword)
            } else {
                Toast.makeText(requireContext(), "Enter correct data!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}