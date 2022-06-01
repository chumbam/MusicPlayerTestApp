package ru.isaev.musicplayertestapp.adapter

import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.isaev.musicplayertestapp.R
import ru.isaev.musicplayertestapp.model.Result
import ru.isaev.musicplayertestapp.model.SearchMusicResponse
import java.util.concurrent.TimeUnit

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.previewUrl == newItem.previewUrl
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_song_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        val song = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(song.artworkUrl100).into(this.findViewById(R.id.rv_music_img))
            findViewById<TextView>(R.id.rv_track_name).apply {
                text = song.trackName
                isSelected = true
            }
            findViewById<TextView>(R.id.rv_artist_name).text = song.artistName
            findViewById<TextView>(R.id.rv_song_duration).text = minutes(song.trackTimeMillis)

            setOnClickListener {
                onItemClickListener?.let { it(song) }
            }
        }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null
    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    private fun minutes(millis: Int): String =
        String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis.toLong()))

}