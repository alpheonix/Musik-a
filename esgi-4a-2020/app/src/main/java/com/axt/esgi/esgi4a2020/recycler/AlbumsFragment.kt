package com.axt.esgi.esgi4a2020.recycler

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.common.showError
import com.axt.esgi.esgi4a2020.data.api.DeezerProvider
import com.axt.esgi.esgi4a2020.data.api.Listener
import com.axt.esgi.esgi4a2020.data.api.Listener2
import com.axt.esgi.esgi4a2020.data.model.Album
import com.axt.esgi.esgi4a2020.data.model.Tracks


/**
 * A simple [Fragment] subclass.
 */
 class AlbumsFragment : Fragment() {

    private lateinit var photosRecyclerView: RecyclerView
    private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter() }
    val background = ColorDrawable(Color.RED)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photosRecyclerView = view.findViewById(R.id.photos_rcv)
        initRecyclerView()

        getAlbums()
    }

    private fun getAlbums() {
        DeezerProvider.getAlbum(object : Listener2<List<Album>> {
            override fun onSuccess(data: List<Album>) {
                albumsAdapter.data = data as ArrayList<Album>
            }

            override fun onError(t: Throwable) {
                showError(t)
            }
        })
    }

    private fun initRecyclerView() {
        photosRecyclerView.layoutManager = LinearLayoutManager(context)
        photosRecyclerView.adapter = albumsAdapter
        photosRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )



        albumsAdapter.listener = this::navigateToDetail
    }

    private fun navigateToDetail(album: Album) {
        findNavController().navigate(
            AlbumsFragmentDirections.actionPhotosFragmentToPhotoDetailFragment(
                album.id,
                album.title,
                album.coverMedium,
                album.releaseDate

            )
        )
    }
}
