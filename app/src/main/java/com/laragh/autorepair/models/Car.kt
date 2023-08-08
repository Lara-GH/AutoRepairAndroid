package com.laragh.autorepair.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Car(
    val engine: String? = null,
    val make: String? = null,
    val model: String? = null,
    val year: String? = null
)
