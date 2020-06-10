package com.axt.esgi.esgi4a2020

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NextNotificationReciver:BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.getStringExtra("action")

        if (action == "next") {
            Myapp.next(context)
        } else if (action == "previous") {
            Myapp.previous(context)
        }else if (action == "play"){
            Myapp.playPause()
        }
    }
}