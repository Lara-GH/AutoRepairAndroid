package com.laragh.autorepair.user.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val tasks: List<Task>? = null
)