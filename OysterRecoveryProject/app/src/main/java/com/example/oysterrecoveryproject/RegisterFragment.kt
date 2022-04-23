package com.example.oysterrecoveryproject

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText
    private lateinit var mConfirmPassword: EditText
    //private lateinit var progressBar: ProgressBar

    private var mAuth: FirebaseAuth? = null

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

        mAuth = FirebaseAuth.getInstance()

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
        //progressBar.visibility = View.VISIBLE

        val email: String = mUsername.text.toString()
        val password: String = mPassword.text.toString()
        val confpassword: String = mConfirmPassword.text.toString()

        if (email.isEmpty()) {
            mUsername.error = "Please enter a username"
            return
        }
        if (password.isEmpty()) {
            mPassword.error = "Please enter a password"
            return
        }
        if (confpassword.isEmpty()) {
            mConfirmPassword.error = "Please confirm password"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.text.toString()).matches()) {
            mUsername.error = "Please enter a valid email address"
            return
        }
        if (mPassword.text.toString() != mConfirmPassword.text.toString()) {
            mConfirmPassword.error = "passwords do not match"
            return
        }
        val x = mAuth!!.createUserWithEmailAndPassword(email, password)

        x.addOnCompleteListener { task ->
            //progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT).show()
                var navRegister = activity as FragNav
                navRegister.navigateFrag(LoginFragment(), false)
                //startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            } else {
                Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}
//        when {

//            email.isEmpty() -> {
//                mUsername.error = "Please enter a username"
//            }
//            mPassword.text.isEmpty() -> {
//                mPassword.error = "Please enter a password"
//            }
//            mConfirmPassword.text.isEmpty() -> {
//                mConfirmPassword.error = "Please confirm password"
//            }

            // add logic for radio buttons

//            mUsername.text.toString().isNotEmpty() && mPassword.text.toString().isNotEmpty() &&
//                    mConfirmPassword.text.toString().isNotEmpty() -> {
//
//                val x = mAuth!!.createUserWithEmailAndPassword(mUsername.text.toString(), mPassword.text.toString())
//
//                x.addOnCompleteListener { task ->
//                    progressBar.visibility = View.GONE
//                    if (task.isSuccessful) {
//                        Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT).show()
//                        var navRegister = activity as FragNav
//                        navRegister.navigateFrag(LoginFragment(), false)
//                    } else {
//                        //Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
//                        if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.text.toString()).matches()) {
//                            mUsername.error = "Please enter a valid email address"
//                        } else if (mPassword.text.toString() != mConfirmPassword.text.toString()) {
//                             mConfirmPassword.error = "passwords do not match"
//                        }
//                    }
//                }

//                if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.text.toString()).matches()) {
//                    mUsername.error = "Please enter a valid email address"
//                } else if (mPassword.text.toString() != mConfirmPassword.text.toString()) {
//                     mConfirmPassword.error = "passwords do not match"
//                } else {
//                    Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT).show()
//                }
