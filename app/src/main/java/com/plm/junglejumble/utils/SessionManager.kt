package com.plm.junglejumble.utils

import com.plm.junglejumble.database.models.User

object SessionManager {
    var currentUser: User? = null
}