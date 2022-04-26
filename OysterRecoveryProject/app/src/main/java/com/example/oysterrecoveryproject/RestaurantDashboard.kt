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
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text

class RestaurantDashboard : Fragment() {
    private lateinit var mAddOneButton: Button
    private lateinit var mAddFiveButton: Button
    private lateinit var mAddTenButton: Button
    private lateinit var mSubOneButton: Button
    private lateinit var mSubFiveButton: Button
    private lateinit var mSubTenButton: Button
    private lateinit var mDisplayCount: TextView
    private lateinit var mDisplayName: TextView
    private var mAuth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private lateinit var shellCount: String
    private lateinit var restName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_restaurant_dashboard, container, false)

        mDisplayName = view.findViewById(R.id.restaurantName)
        mDisplayCount = view.findViewById(R.id.displayCount)

        mAddOneButton = view.findViewById(R.id.addOneButton)
        mAddFiveButton = view.findViewById(R.id.addFiveButton)
        mAddTenButton = view.findViewById(R.id.addTenButton)

        mSubOneButton = view.findViewById(R.id.subOneButton)
        mSubFiveButton = view.findViewById(R.id.subFiveButton)
        mSubTenButton = view.findViewById(R.id.subTenButton)

        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")
        shellCount = ""
        restName = ""
        view.setBackgroundResource(com.google.android.material.R.color.cardview_dark_background)

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
            var navLogin = activity as FragNav
            navLogin.navigateFrag(LoginFragment(), addToStack = false)
        }
        fun initializeCount(): String {
            val x = mAuth!!.currentUser
            val user = x!!.uid
            database.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    val count = it.child("shells").value
                    shellCount = count.toString()
                    mDisplayCount.setText(shellCount)
                }else{
                    Toast.makeText(context, "Shell Count not found", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                Toast.makeText(context, "Failed to find user", Toast.LENGTH_LONG).show()
            }
            return shellCount
        }
        fun initializeName(): String {
            val x = mAuth!!.currentUser
            val user = x!!.uid
            database.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    val name = it.child("name").value
                    restName = name.toString()
                    mDisplayName.setText(restName)
                }else{
                    Toast.makeText(context, "Shell Count not found", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                Toast.makeText(context, "Failed to find user", Toast.LENGTH_LONG).show()
            }
            return restName
        }
        mAddOneButton.setOnClickListener{
            Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
        }

        //Connects and displays restaurant name and shell count from firebase database
        initializeCount()
        initializeName()

        return view
    }


}