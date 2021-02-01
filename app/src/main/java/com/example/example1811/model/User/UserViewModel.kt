package com.example.example1811.model.User

import androidx.lifecycle.ViewModel
import com.example.example1811.Repository.UserRepo
import com.example.example1811.db.User.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepo: UserRepo) : ViewModel() {
    
    val id = userRepo.id
    
    val isAuthed = userRepo.isAuthed
    
    fun insert(user: User) = GlobalScope.launch {
        userRepo.addUser(user)
    }

    fun delete() = GlobalScope.launch {
        userRepo.delete()
    }
}