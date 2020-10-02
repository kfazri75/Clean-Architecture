package com.nginapps.capstone.extension

import android.app.Activity
import com.shreyaspatil.MaterialDialog.MaterialDialog

fun Activity.showAlertDialog(title: String, message: String) {
    MaterialDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Close") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
}