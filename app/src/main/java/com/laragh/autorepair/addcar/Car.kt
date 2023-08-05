package com.laragh.autorepair.addcar

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Car(
    val engine: List<String>? = null,
    val model: String? = null
)