package com.axt.esgi.esgi4a2020.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.data.model.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.PhotoViewHolder>() {

    var data: ArrayList<Album> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((Album) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return PhotoViewHolder(view)
    }
    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = data[position]

        with(holder) {
            titleTv.text = photo.title
            Glide.with(itemView)
                .load(photo.coverSmall)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(thumbnailImv)

            itemView.setOnClickListener { listener?.invoke(photo) }
        }

    }


    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnailImv: ImageView = itemView.findViewById(R.id.item_photo_imv)
        var titleTv: TextView = itemView.findViewById(R.id.item_photo_tv)
    }
}