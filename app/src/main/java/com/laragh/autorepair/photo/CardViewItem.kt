package com.laragh.autorepair.photo

import android.net.Uri

data class CardViewItem(
    val type: Int? = null,
    val uri: Uri? = null
) {
    companion object {
        const val EMPTY_TYPE = 0
        const val IMAGE_TYPE = 1
    }
}