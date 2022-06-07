package ru.isaev.musicplayertestapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.isaev.musicplayertestapp.databinding.ItemSongCellBinding
import ru.isaev.musicplayertestapp.model.Result
import ru.isaev.musicplayertestapp.utils.Converter

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

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
        val binding =
            ItemSongCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val song = differ.currentList[position]
        holder.bind(song)
    }

    inner class TrackViewHolder(private val itemBinding: ItemSongCellBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(song: Result) {
            itemBinding.apply {
                Glide.with(itemView).load(song.artworkUrl100).into(rvMusicImg)
                rvTrackName.text = song.trackName
                rvArtistName.text = song.artistName
                rvSongDuration.text = Converter.getMinutes(song.trackTimeMillis.toLong())
                rvTrackName.isSelected = true
                itemView.setOnClickListener {
                    onItemClickListener?.let { it(song) }
                }
            }
        }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null
    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }
}