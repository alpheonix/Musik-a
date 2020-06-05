package com.axt.esgi.esgi4a2020.recycler


import android.annotation.SuppressLint
import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.Myapp
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.data.model.Tracks


class TracksAdapter : RecyclerView.Adapter<TracksAdapter.PhotoViewHolder>() {

    var data: ArrayList<Tracks> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((Tracks) -> Unit)? = null

    lateinit var notifManager :NotificationManager


    override fun getItemCount() = data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
            Myapp.createPlayer(data)




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

                Myapp.play(position)



            Log.d("onclick",track.title)
        }

    }



    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var timeTv: TextView = itemView.findViewById(R.id.item_time_tv)
        var titleTv: TextView = itemView.findViewById(R.id.item_title_tv)


    }
}
