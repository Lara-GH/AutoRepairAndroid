package com.laragh.autorepair.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Car(
    val year: String? = null,
    val make: String? = null,
    val model: String? = null,
    val engine: String? = null,
    var addedPhotos: Boolean = false,
    var id: String = ""
)
