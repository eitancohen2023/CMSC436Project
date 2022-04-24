package com.example.oysterrecoveryproject

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var choice: RadioButton
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText
    private lateinit var mRegButton: Button
    private var userType: Int = 0

    private var mAuth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        mUsername = view.findViewById(R.id.register_username)
        mPassword = view.findViewById(R.id.register_password)
        mRegButton = view.findViewById(R.id.register_button)
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")
        mRadioGroup = view.findViewById(R.id.radio_group)

        view.findViewById<Button>(R.id.back_to_login_button).setOnClickListener {
            var navRegister = activity as FragNav
            navRegister.navigateFrag(LoginFragment(), false)
        }

        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            registerNewUser()
        }
        return view
    }

    private fun registerNewUser() {
        val email: String = mUsername.text.toString()
        val password: String = mPassword.text.toString()
        val radioButtonID = mRadioGroup.checkedRadioButtonId
        val radioButton = mRadioGroup.findViewById<View>(radioButtonID) as RadioButton
        val selectedText = radioButton.text as String

        // assign type for restaurant (1) or truck driver (2)
        userType = if (selectedText == "Restaurant") {
            1
        } else {
            2
        }

        if (email.isEmpty()) {
            mUsername.error = "Please enter a username"
            return
        }
        if (password.isEmpty()) {
            mPassword.error = "Please enter a password"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.text.toString()).matches()) {
            mUsername.error = "Please enter a valid email address"
            return
        }

        val x = mAuth!!.createUserWithEmailAndPassword(email, password)
        // Make sure register button is turned off for this user
        mRegButton.isEnabled = false
        mRegButton.alpha = 0.5f

        x.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // add new user to real time database
                val fUser = mAuth!!.currentUser
                val newUser = User(email, password, userType)
                database.child(fUser!!.uid).setValue(newUser)

                // navigate back to login
                Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT).show()
                var navDashboard = activity as FragNav
                navDashboard.navigateFrag(LoginFragment(), true)
            } else {
                // Turn register button back on if failed
                mRegButton.isEnabled = true
                mRegButton.alpha = 1.0f
                Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    data class User(val email: String? = "", val password: String, private var type_: Int = 0){
//                    val address: String? = "", val name: String?, val shells: Int = 0) {
        val type: Int get() = type_
    }

}

