package com.example.example1811.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.example1811.R

open class FragmentExtension {

    fun Fragment.addFragment(fragment: Fragment, targetFragment: Fragment, requestCode: Int, isAddToBackStack: Boolean = false, init: Bundle.() -> Unit = {}) {
        requireFragmentManager().inTransaction {
            val bundle = Bundle()
            bundle.init()
            fragment.arguments = bundle
            setTargetFragment(targetFragment,requestCode)
            if (isAddToBackStack) {
                addToBackStack(fragment::class.java.simpleName)
            }
            add(R.id.content, fragment, fragment::class.java.simpleName)
        }
    }


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    fun Fragment.removeFragment(fragment: Fragment, enter: Int = 0, exit: Int = 0) {
        requireFragmentManager().inTransaction {
            remove(fragment)
        }
    }
}