package com.laragh.autorepair.user.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Task(
    var id: String? = null,
    val status: String? = null,
    val date: String? = null,
    val description: String? = null
)