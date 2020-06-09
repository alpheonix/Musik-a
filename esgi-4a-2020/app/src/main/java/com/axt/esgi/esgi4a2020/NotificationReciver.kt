package com.axt.esgi.esgi4a2020

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReciver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(p0,"TESTTTT",Toast.LENGTH_LONG).show()
    }


}