package com.axt.esgi.esgi4a2020

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    createNotificationChannel()
    val next_image_view = findViewById(R.id.next_button) as ImageView
    val play_image_view = findViewById(R.id.play_button) as ImageView
    val previous_image_view = findViewById(R.id.previous_button) as ImageView





    next_image_view.setOnClickListener {
      Toast.makeText(this,"Chanson suivante",Toast.LENGTH_LONG).show()
      Myapp.next(this)
    }
    previous_image_view.setOnClickListener {
      Toast.makeText(this,"Chanson precedente",Toast.LENGTH_LONG).show()

      Myapp.previous(this)
    }

    play_image_view.setOnClickListener {

      Myapp.playPause()
    }

  }

  private fun createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = getString(R.string.channel_name)
      val descriptionText = getString(R.string.channel_description)
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
        description = descriptionText
      }
      // Register the channel with the system
      val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }


}
