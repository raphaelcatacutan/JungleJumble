package com.plm.junglejumble.utils

import com.plm.junglejumble.database.models.User
import com.plm.junglejumble.database.viewmodels.ScoreViewModel
import com.plm.junglejumble.database.viewmodels.UserViewModel

object SessionManager {
    var userViewModel: UserViewModel? = null
    var scoreViewModel: ScoreViewModel? = null
    var currentUser: User? = null
}