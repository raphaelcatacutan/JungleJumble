package com.plm.junglejumble.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plm.junglejumble.database.dao.ScoreDao
import com.plm.junglejumble.database.models.Score
import kotlinx.coroutines.launch

class ScoreViewModel(private val dao: ScoreDao) : ViewModel() {
    var scores = mutableListOf<Score>()
        private set

    fun loadScores() {
        viewModelScope.launch {
            scores = dao.getAllScores().toMutableList()
        }
    }

    fun addScore(score: Score) {
        viewModelScope.launch {
            dao.insert(score)
            loadScores()
        }
    }
}
