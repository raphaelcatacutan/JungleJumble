package com.plm.junglejumble.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plm.junglejumble.database.dao.ScoreDao
import com.plm.junglejumble.database.dao.UserDao

class ScoreViewModelFactory(private val dao: ScoreDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
