package com.laragh.autorepair.user.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Task(
    val id: String? = null,
    val description: String? = null
)