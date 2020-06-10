package com.axt.esgi.esgi4a2020


import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.axt.esgi.esgi4a2020.data.model.Player
import com.axt.esgi.esgi4a2020.data.model.Tracks


class Myapp : Application() {
    companion object {
        lateinit var player: Player
        var mediaPlayer = MediaPlayer()
        fun createPlayer(tracks: ArrayList<Tracks>) {
            player = Player(tracks,0,0)
        }
        lateinit var builder : NotificationCompat.Builder
        fun createNotif(context: Context, position: Int){

            val previousintent = Intent(context,PreviousNotificationReciver::class.java)
            previousintent.putExtra("action","previous")
            val    previousPendingintent = PendingIntent.getBroadcast(context,0,previousintent,PendingIntent.FLAG_UPDATE_CURRENT)

            val playintent = Intent(context,PlayNotificationReciver::class.java)
            playintent.putExtra("action","play")
            val    playPendingintent = PendingIntent.getBroadcast(context,0,playintent,PendingIntent.FLAG_UPDATE_CURRENT)
            
            val nextintent = Intent(context,NextNotificationReciver::class.java)
            nextintent.putExtra("action","next")
            val    nextPendingintent = PendingIntent.getBroadcast(context,0,nextintent,PendingIntent.FLAG_UPDATE_CURRENT)
            
            val notificationLayout = RemoteViews("com.axt.esgi.esgi4a2020", R.layout.activity_notification)
                notificationLayout.setTextViewText(R.id.notification_title,player.tracks[position].title)
            

            builder = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.next)

                 .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                 .setContentTitle(player.tracks[position].title)
                 .setContent(notificationLayout)
                .setSound(null)
                
                 .setOngoing(true)

            notificationLayout.setOnClickPendingIntent(R.id.notif_play_button,playPendingintent)
            notificationLayout.setOnClickPendingIntent(R.id.notif_previous_button,previousPendingintent)
            notificationLayout.setOnClickPendingIntent(R.id.notif_next_button,nextPendingintent)
            

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                var notificationId = 1
                notify(notificationId, builder.build())
            }
        }
        fun play( trackId: Int ) {
            player.position = trackId
            Log.d("position",trackId.toString())
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()

                mediaPlayer.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(player.tracks[trackId].preview)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
            } else {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(player.tracks[trackId].preview)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
            }


        }
        
        fun next(context: Context){

            if (player.position == player.tracks.size-1){
                player.position = 0
            }else{
                player.position +=1
            }

            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(player.tracks[player.position].preview)
                prepare() // might take long! (for buffering, etc)
                start()
            }
            createNotif(context, player.position)
        }

        fun previous(context: Context){
            if (player.position == 0){
                player.position = player.tracks.size-1
            }else{
                player.position -=1
            }
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(player.tracks[player.position].preview)
                prepare() // might take long! (for buffering, etc)
                start()
            }
            createNotif(context, player.position)

        }

        fun playPause(){
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                player.musicTime  = mediaPlayer.getCurrentPosition()
            }else{
                mediaPlayer.seekTo(player.musicTime)
                mediaPlayer.start()
            }
        }

    }
}



