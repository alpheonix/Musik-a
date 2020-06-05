package com.axt.esgi.esgi4a2020

import android.app.Application
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.axt.esgi.esgi4a2020.data.model.Player
import com.axt.esgi.esgi4a2020.data.model.Tracks

class Myapp : Application() {
    companion object {
        lateinit var player: Player
        var mediaPlayer = MediaPlayer()
        fun createPlayer(tracks: ArrayList<Tracks>) {
            player = Player(tracks)
        }

        fun play( trackId: Int) {
            Log.d("playing", mediaPlayer.isPlaying.toString())
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
    }
}


