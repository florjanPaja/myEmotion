package com.example.myemotion.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.myemotion.R

object AvvisiUtils {
fun showMessage(context: Context, messaggio: String?) {
    val dialog = Dialog(context, R.style.CustomDialogTheme) // Usa il tema personalizzato
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.popup)

    val layoutParams = dialog.window?.attributes
    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
    layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams?.gravity = Gravity.CENTER
    dialog.window?.attributes = layoutParams

    val messageTextView = dialog.findViewById<TextView>(R.id.messageTextView)
            messageTextView.text = messaggio

    val closeButton = dialog.findViewById<TextView>(R.id.closeButton)
            closeButton.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}
}

