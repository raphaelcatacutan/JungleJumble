package com.plm.junglejumble.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plm.junglejumble.database.dao.UserDao

class UserViewModelFactory(private val dao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
