package com.example.oysterrecoveryproject

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class TruckDriverDashboard : Fragment() {

    private lateinit var getNxtRestBttn: Button
    private var mAuth: FirebaseAuth? = null
    private lateinit var resturantsLeft: TextView
    private lateinit var resturantDesc: TextView
    private lateinit var database: DatabaseReference
    private var userList = ArrayList<User>()
    private lateinit var goToMapButt: Button
    private var currUser: User? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_truck_driver_dashboard, container, false)

        getNxtRestBttn = view.findViewById(R.id.nxtRestBttn) as Button
        resturantsLeft = view.findViewById(R.id.restaurantsLeft)
        resturantDesc = view.findViewById(R.id.resturantDesc)
        goToMapButt = view.findViewById(R.id.goToMaps) as Button
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")

        // get users to deliver too if above a threshold of shells
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var iter = 0
                for(user in dataSnapshot.children) {
                    when (user.child("type").value.toString()) {
                        "1" -> {
                            if(user.child("shells").value != null){
                                if (user.child("shells").value.toString().toInt() > 100) {
                                    val u = User(
                                        user.child("address").value.toString(),
                                        user.child("name").value.toString())
                                    userList.add(u)
                                    iter++
                                }
                            }
                        }
                        "2" -> {
                            //skip because trucker
                        }
                    }
                    if (iter == 5) {
                        break
                    }
                }
                updateDescs()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



        getNxtRestBttn.setOnClickListener{
                updateDescs()
        }

        goToMapButt.setOnClickListener{
            if(userList.isEmpty()){
                sendToMaps("7649 S Library Ln, College Park, MD 20742")
            } else {
                sendToMaps(currUser!!.address)
            }
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            signOut()
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    fun setDropOff(){
        val add = "7649 S Library Ln, College Park, MD 20742"
        resturantsLeft.text = "There are no more locations left! Go to drop off!"
        resturantDesc.text = "Name: Drop off\nAddress: $add"
    }

    // update all fields
    fun updateDescs() {
        if (userList.isEmpty()) {
            setDropOff()
        } else {
        currUser = userList.removeAt(0)
        resturantsLeft.text = "There are " + (userList.size + 1) + " left!"
        resturantDesc.text = "Name: " + currUser!!.name + "\n" + "Address: " + currUser!!.address
        }
    }


    // sign out of firebase and go back to login screen
    fun signOut() {
        Firebase.auth.signOut()
        var navLogin = activity as FragNav
        navLogin.navigateFrag(LoginFragment(), addToStack = false)
    }

    // Parse the address then send to Google Maps for Trucker to go to
    fun sendToMaps(address: String) {
        val newAdd = address.replace(' ', '+')
        val geoIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$newAdd"))
        startActivity(geoIntent)
    }

    data class User(val address:String,val name:String)


// save for later
//https://stackoverflow.com/questions/22246188/android-google-maps-draw-path-between-multiple-points
}