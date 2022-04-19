package com.example.oysterrecoveryproject

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class RegisterFragment : Fragment() {
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText
    private lateinit var mConfirmPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        mRadioGroup = view.findViewById(R.id.radio_group)
        mUsername = view.findViewById(R.id.register_username)
        mPassword = view.findViewById(R.id.register_password)
        mConfirmPassword = view.findViewById(R.id.register_confirm_password)

        view.findViewById<Button>(R.id.back_to_login_button).setOnClickListener {
            var navRegister = activity as FragNav
            navRegister.navigateFrag(LoginFragment(), false)
        }

        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            registerNewUser()
            val selectedOption: Int = mRadioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            radioButton = view.findViewById(selectedOption)

            // Displaying text of the checked radio button in the form of toast
            Toast.makeText(context, radioButton.text, Toast.LENGTH_SHORT).show()

        }
        return view
    }

    private fun registerNewUser() {
        when {
            mUsername.text.isEmpty() -> {
                mUsername.error = "Please enter a username"
            }
            mPassword.text.isEmpty() -> {
                mPassword.error = "Please enter a password"
            }
            mConfirmPassword.text.isEmpty() -> {
                mConfirmPassword.error = "Please confirm password"
            }

            // add logic for radio buttons

            mUsername.text.toString().isNotEmpty() && mPassword.text.toString().isNotEmpty() &&
                    mConfirmPassword.text.toString().isNotEmpty() -> {

                if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.text.toString()).matches()) {
                    mUsername.error = "Please enter a valid email address"
                } else if (mPassword.text.toString() != mConfirmPassword.text.toString()) {
                     mConfirmPassword.error = "passwords do not match"
                } else {
                    Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT).show()
                }
                    }
        }
    }
}