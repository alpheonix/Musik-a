package com.axt.esgi.esgi4a2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Notification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val next_image_view = findViewById(R.id.notif_next_button) as ImageView
        val play_image_view = findViewById(R.id.notif_play_button) as ImageView
        val previous_image_view = findViewById(R.id.notif_previous_button) as ImageView

        val notif_title_image_view = findViewById(R.id.notification_title) as TextView
            notif_title_image_view.text = "yoooooooo"

        next_image_view.setOnClickListener {
            Toast.makeText(this,"Chanson suivante", Toast.LENGTH_LONG).show()
            Myapp.next(this)
        }
        previous_image_view.setOnClickListener {
            Toast.makeText(this,"Chanson precedente", Toast.LENGTH_LONG).show()

            Myapp.previous(this)
        }

        play_image_view.setOnClickListener {

            Myapp.playPause()
        }
    }
}
