package com.example.oysterrecoveryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), FragNav {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth
        if (mAuth.currentUser != null) {
            val registeredUserID = mAuth.currentUser!!.uid
            // Determine type of user and redirect to correct dashboard
            val db = FirebaseDatabase.getInstance().reference.child("users").child(registeredUserID)
            db.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    when (dataSnapshot.child("type").value.toString()) {
                        "1" -> {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.container, RestaurantDashboard()).addToBackStack(null)
                                .commit()
                        }
                        "2" -> {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.container, TruckDriverDashboard()).addToBackStack(null)
                                .commit()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}

// Realtime Database link -> https://oysterrecoverypartnershi-f47db-default-rtdb.firebaseio.com/
