package com.example.example1811.Repository

import com.example.example1811.db.User.User
import com.example.example1811.db.User.UserDao

class UserRepo(private val userDao: UserDao)  {

    val id = userDao.getId()
    val isAuthed = userDao.getIsAuthed()

    suspend fun addUser(user: User) : Long {
        return  userDao.insert(user)
    }
    suspend fun delete(): Int{
        return userDao.delete()
    }

}