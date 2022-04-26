package com.example.oysterrecoveryproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class RestaurantDashboard : Fragment() {
    private lateinit var mAddOneButton: Button
    private lateinit var mDisplayCount: TextView
    private var mAuth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private lateinit var shellCount: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_restaurant_dashboard, container, false)

        mDisplayCount = view.findViewById(R.id.displayCount)
        mAddOneButton = view.findViewById(R.id.addOneButon)
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
            var navLogin = activity as FragNav
            navLogin.navigateFrag(LoginFragment(), addToStack = false)
        }

        fun findCount() {
            val x = mAuth!!.currentUser
            val user = x!!.uid
            database.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    val count = it.child("shells").value
                    shellCount = count.toString()
                    Toast.makeText(context, shellCount, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Shell Count not found", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                Toast.makeText(context, "Failed to find user", Toast.LENGTH_LONG).show()
            }
        }

        mAddOneButton.setOnClickListener{
            findCount()
        }


        return view
    }


}