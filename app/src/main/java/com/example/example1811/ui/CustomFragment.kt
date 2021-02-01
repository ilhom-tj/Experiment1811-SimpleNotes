package com.example.example1811.ui

import androidx.fragment.app.Fragment

open class CustomFragment:  Fragment() {

     open fun onBackPressed(): Boolean {
        return false
    }
}