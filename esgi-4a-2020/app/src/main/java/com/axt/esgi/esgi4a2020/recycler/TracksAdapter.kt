package com.axt.esgi.esgi4a2020.recycler


import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.data.model.Tracks
import com.axt.esgi.esgi4a2020.recycler.detail.AlbumDetailFragment
import java.io.IOException


class TracksAdapter : RecyclerView.Adapter<TracksAdapter.PhotoViewHolder>() {

    var data: ArrayList<Tracks> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((Tracks) -> Unit)? = null
    var mediaPlayer = MediaPlayer()



    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return PhotoViewHolder(view)
    }
    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val track = data[position]

        with(holder) {

            titleTv.text = track.title
            timeTv.text = "${track.duration} secondes"
            timeTv.setOnClickListener { listener?.invoke(track) }
        }

        holder.itemView.setOnClickListener{
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(track.preview)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
            }else {
                mediaPlayer.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(track.preview)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
            }

            Log.d("onclick",track.title)
        }

    }



    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var timeTv: TextView = itemView.findViewById(R.id.item_time_tv)
        var titleTv: TextView = itemView.findViewById(R.id.item_title_tv)


    }
}
