package com.example.oysterrecoveryproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        mAuth = FirebaseAuth.getInstance()

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
        val email: String = mUsername.text.toString()
        val password: String = mPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }
//        when {
//            mUsername.text.isEmpty() -> {
//                mUsername.error = "Please enter a username"
//            }
//            mPassword.text.isEmpty() -> {
//                mPassword.error = "Please enter a password"
//            }
//            mUsername.text.toString().isNotEmpty() && mPassword.text.toString().isNotEmpty() -> {
//                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//            }
//        }
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                } else {
                    Toast.makeText(context,"Login failed! Please try again later", Toast.LENGTH_SHORT).show()
                }
            }
    }


}