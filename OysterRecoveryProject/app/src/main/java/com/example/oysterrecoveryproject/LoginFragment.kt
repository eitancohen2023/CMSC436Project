package com.example.oysterrecoveryproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        mUsername = view.findViewById(R.id.login_username)
        mPassword = view.findViewById(R.id.login_password)

        view.findViewById<Button>(R.id.buttonRegister).setOnClickListener {
            var navRegister = activity as FragNav
            navRegister.navigateFrag(RegisterFragment(), false)
        }

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            loginUser()
        }

        return view
    }

    private fun loginUser() {
        when {
            mUsername.text.isEmpty() -> {
                mUsername.error = "Please enter a username"
            }
            mPassword.text.isEmpty() -> {
                mPassword.error = "Please enter a password"
            }
            mUsername.text.toString().isNotEmpty() && mPassword.text.toString().isNotEmpty() -> {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }


}