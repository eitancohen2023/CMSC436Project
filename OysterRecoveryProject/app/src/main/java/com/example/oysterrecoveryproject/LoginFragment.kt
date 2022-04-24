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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText
    private var mAuth: FirebaseAuth? = null
    private lateinit var mloginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        mAuth = Firebase.auth
        mloginButton = view.findViewById(R.id.buttonLogin)
        mUsername = view.findViewById(R.id.login_username)
        mPassword = view.findViewById(R.id.login_password)

        view.findViewById<Button>(R.id.buttonRegister).setOnClickListener {
            var navRegister = activity as FragNav
            navRegister.navigateFrag(RegisterFragment(), false)
        }

        mloginButton.setOnClickListener{
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

        val x = mAuth!!.signInWithEmailAndPassword(email, password)

        // Make sure register button is turned off for this user
        mloginButton.isEnabled = false
        mloginButton.alpha = 0.5f

        x.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val registeredUserID = currentUser!!.uid

                // Determine type of user and redirect to correct dashboard
                val db = FirebaseDatabase.getInstance().reference.child("users").child(registeredUserID)
                db.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        when (dataSnapshot.child("type").value.toString()) {
                            "1" -> {
                                Toast.makeText(context, "You are logged in as a restaurant!", Toast.LENGTH_SHORT).show()
                                var navDashboard = activity as FragNav
                                navDashboard.navigateFrag(RestaurantDashboard(), true)
                            }
                            "2" -> {
                                Toast.makeText(context, "You are logged in as a truck driver!", Toast.LENGTH_SHORT).show()
                                var navDashboard = activity as FragNav
                                navDashboard.navigateFrag(TruckDriverDashboard(), true)
                            }
                            else -> {
                                Toast.makeText(context,"Failed Login. Please Try Again", Toast.LENGTH_SHORT).show()
                                mloginButton.isEnabled = true
                                mloginButton.alpha = 1.0f
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            } else {
                // Turn register button back on if failed
                mloginButton.isEnabled = true
                mloginButton.alpha = 1.0f
                Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
            }
        }
    }


}