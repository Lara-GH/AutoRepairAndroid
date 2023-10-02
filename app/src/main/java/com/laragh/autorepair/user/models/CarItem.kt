package com.laragh.autorepair.user.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CarItem(
    val engine: List<String>? = null,
    val model: String? = null,
)