package com.example.oysterrecoveryproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RestaurantDashboard : Fragment() {
    private lateinit var mAddOneButton: Button
    private lateinit var mAddFiveButton: Button
    private lateinit var mAddTenButton: Button
    private lateinit var mSubOneButton: Button
    private lateinit var mSubFiveButton: Button
    private lateinit var mSubTenButton: Button
    private lateinit var mDisplayCount: TextView
    private lateinit var mResetCount: TextView
    private lateinit var mDisplayName: TextView
    private lateinit var mSetCount: EditText
    private lateinit var mSubmitCount: Button
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

        //TextViews for live counts
        mDisplayName = view.findViewById(R.id.restaurantName)
        mDisplayCount = view.findViewById(R.id.displayCount)

        //Set&Reset Buttons
        mResetCount = view.findViewById(R.id.resetCount)
        mSetCount = view.findViewById(R.id.setCount)
        mSubmitCount = view.findViewById(R.id.submitCount)

        //Increase Buttons
        mAddOneButton = view.findViewById(R.id.addOneButton)
        mAddFiveButton = view.findViewById(R.id.addFiveButton)
        mAddTenButton = view.findViewById(R.id.addTenButton)

        //Decrease Buttons
        mSubOneButton = view.findViewById(R.id.subOneButton)
        mSubFiveButton = view.findViewById(R.id.subFiveButton)
        mSubTenButton = view.findViewById(R.id.subTenButton)

        //Firebase connection
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance().getReference("users")

        //Temporarily assign TextViews
        shellCount = ""
        restName = ""

        view.setBackgroundResource(com.google.android.material.R.color.cardview_dark_background)

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
            var navLogin = activity as FragNav
            navLogin.navigateFrag(LoginFragment(), true)
        }

        //Grab and set Shell Count
        fun initializeCount(): String {
            val user = mAuth!!.currentUser!!.uid
            database.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    var count = it.child("shells").value
                    if (count == ""){
                        count = 0
                    }
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

        //Grab and set Restaurant Name
        fun initializeName(): String {
            val user = mAuth!!.currentUser!!.uid
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

        //Buttons for adding/subtracting shells
        mAddOneButton.setOnClickListener{
            updateCount(1)
        }
        mAddFiveButton.setOnClickListener{
            updateCount(5)
        }
        mAddTenButton.setOnClickListener{
            updateCount(10)
        }
        mSubOneButton.setOnClickListener{
            updateCount(-1)
        }
        mSubFiveButton.setOnClickListener{
            updateCount(-5)
        }
        mSubTenButton.setOnClickListener{
            updateCount(-10)
        }
        mResetCount.setOnClickListener{
            updateCount(RESET_CODE)
        }
        mSubmitCount.setOnClickListener{
            hideKeyboard(requireContext(), view)
            var text = mSetCount.text.toString()
            setCount(text)
            mSetCount.setText("")
        }

        //Connects and displays restaurant name and shell count from firebase database
        initializeCount()
        initializeName()

        return view
    }
    //Updates the current shell count with specified value
    private fun updateCount(x: Int) {
        val currCount = shellCount.toInt()
        var newCount = currCount + x
        if(newCount < 0 || x == RESET_CODE) {
            newCount = 0
        }
        val newString = newCount.toString()
        val user = mAuth!!.currentUser!!.uid
        database.child(user).child("shells").setValue(newString)
    }

    private fun setCount(x : String) {
        val user = mAuth!!.currentUser!!.uid
        database.child(user).child("shells").setValue(x)
    }

    //Function was created with help from:
    //https://stackoverflow.com/questions/41790357/close-hide-the-android-soft-keyboard-with-kotlin
    private fun hideKeyboard(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        val RESET_CODE = 2
    }


}