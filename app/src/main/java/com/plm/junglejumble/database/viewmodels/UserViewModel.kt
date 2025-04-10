package com.plm.junglejumble.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plm.junglejumble.database.dao.UserDao
import com.plm.junglejumble.database.models.User
import kotlinx.coroutines.launch

class UserViewModel(private val dao: UserDao) : ViewModel() {
    var users = mutableListOf<User>()
        private set

    fun loadUsers() {
        viewModelScope.launch {
            users = dao.getAllUsers().toMutableList()
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            dao.insert(user)
            loadUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            dao.delete(user)
            loadUsers()
        }
    }
}
