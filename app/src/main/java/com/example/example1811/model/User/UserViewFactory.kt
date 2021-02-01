package com.example.example1811.model.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.example1811.Repository.Repository
import com.example.example1811.Repository.UserRepo
import com.example.example1811.model.NoteViewModel
import java.lang.IllegalArgumentException

class UserViewFactory(private val userRepo: UserRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(userRepo) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}