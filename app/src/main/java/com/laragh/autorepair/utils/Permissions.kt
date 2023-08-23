package com.laragh.autorepair.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.laragh.autorepair.R

class Permissions(
    private val context: Context,
    private val requestMultiplePermissions: ActivityResultLauncher<Array<String>>
) {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                context,
                R.string.permissions_granted,
                Toast.LENGTH_LONG
            ).show()
            return true
        } else {
            Toast.makeText(
                context,
                R.string.you_need_to_accept_permissions,
                Toast.LENGTH_LONG
            ).show()
            requestMultiplePermissions()
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestMultiplePermissions() {
        requestMultiplePermissions.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }
}