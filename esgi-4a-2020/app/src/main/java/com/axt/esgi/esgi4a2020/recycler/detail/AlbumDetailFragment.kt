package com.axt.esgi.esgi4a2020.recycler.detail

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.common.showError
import com.axt.esgi.esgi4a2020.data.api.DeezerProvider
import com.axt.esgi.esgi4a2020.data.api.Listener
import com.axt.esgi.esgi4a2020.data.dto.TracksResponseDTO
import com.axt.esgi.esgi4a2020.data.model.Album
import com.axt.esgi.esgi4a2020.data.model.PhotoDetail
import com.axt.esgi.esgi4a2020.data.model.Tracks
import com.axt.esgi.esgi4a2020.recycler.AlbumsAdapter
import com.axt.esgi.esgi4a2020.recycler.TracksAdapter
import com.bumptech.glide.Glide

private const val TAG_PREFIX = "#"
private const val TAG_SEPARATOR = "  #"

class AlbumDetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var titleTv: TextView
    private lateinit var dateTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var tagsTv: TextView
    private val trackAdapter: TracksAdapter by lazy { TracksAdapter() }
    private lateinit var photosRecyclerView: RecyclerView
    val background = ColorDrawable(Color.RED)


    private val args by navArgs<AlbumDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            imageView = findViewById(R.id.album_detail_imv)
            titleTv = findViewById(R.id.album_detail_title_tv)
            dateTv = findViewById(R.id.album_detail_title_tv)
        }
        photosRecyclerView = view.findViewById(R.id.tracks_rcv)

        updateInfo(args.title,args.coverMedium,args.releaseDate)
        getTracks()
        initRecyclerView()

    }

    private fun getTracks() {
        val albumId = args.photoId
        DeezerProvider.getTracks(albumId, object : Listener<TracksResponseDTO> {
            override fun onSuccess(data: List<Tracks>) {
                trackAdapter.data = data as ArrayList<Tracks>
            }

            override fun onError(t: Throwable) {
                showError(t)
            }

        })
    }


    private fun updateInfo(title:String,coverMedium:String,releaseDate:String) {
        Log.d("this",this.toString())
        Glide.with(this).load(coverMedium).into(imageView)
        titleTv.text = title
        dateTv.text = releaseDate
    }

    private fun initRecyclerView() {
        photosRecyclerView.layoutManager = LinearLayoutManager(context)
        photosRecyclerView.adapter = trackAdapter
        photosRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN or ItemTouchHelper.UP,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    recyclerView.adapter?.notifyItemMoved(
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    return false
                }



                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    trackAdapter.removeItem(viewHolder.adapterPosition)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    if(dX>0){
                        background.setBounds(itemView.left,itemView.top,dX.toInt(),itemView.bottom)
                    }else{
                        background.setBounds(itemView.right +dX.toInt(),itemView.top,itemView.right,itemView.bottom)

                    }

                    background.draw(c)

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

            }
        )
        itemTouchHelper.attachToRecyclerView(photosRecyclerView)

        //trackAdapter.listener = this::navigateToDetail
    }
}
