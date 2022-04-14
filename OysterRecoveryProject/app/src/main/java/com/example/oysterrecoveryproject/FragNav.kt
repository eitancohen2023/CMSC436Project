package com.example.oysterrecoveryproject

import androidx.fragment.app.Fragment

interface FragNav {
    fun navigateFrag(fragment: Fragment, addToStack: Boolean)
}