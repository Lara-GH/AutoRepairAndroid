package com.laragh.autorepair.photo

import android.net.Uri
import com.google.firebase.storage.StorageReference

data class CardViewItem(
    val type: Int? = null,
    val uri: Uri? = null,
    val storageReference: StorageReference? = null
) {
    companion object {
        const val EMPTY_TYPE = 0
        const val URI_TYPE = 1
        const val STORAGE_REFERENCE_TYPE = 2
    }
}