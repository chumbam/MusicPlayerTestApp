package ru.isaev.musicplayertestapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.isaev.musicplayertestapp.R
import ru.isaev.musicplayertestapp.databinding.FragmentMusicPlayerBinding
import ru.isaev.musicplayertestapp.model.Result
import ru.isaev.musicplayertestapp.ui.MainViewModel
import ru.isaev.musicplayertestapp.utils.Converter

class MusicPlayerFragment : Fragment() {

    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "View binding is not initialized" }
    private val viewModel: MainViewModel by activityViewModels()
    private val args: MusicPlayerFragmentArgs by navArgs()
    lateinit var exoPlayer: ExoPlayer
    private var handler: Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trackInstance = args.song
        setupExoPlayer(trackInstance)
        configSeekBar()
        configVisualization()
        handleCurrentTrackTime()
        binding.fmpBackBtn.setOnClickListener { findNavController().popBackStack() }
        exoPlayer.play()
    }

    private fun setupExoPlayer(track: Result) {
        exoPlayer = ExoPlayer.Builder(context!!).build()
        exoPlayer.repeatMode = (Player.REPEAT_MODE_ALL)
        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(args.song.previewUrl)))
        exoPlayer.prepare()
        binding.apply {
            fmpArtistTitle.text = track.artistName
            fmpSongTitle.text = track.trackName
            fmpSongTitle.isSelected = true
            Glide.with(this@MusicPlayerFragment).load(track.artworkUrl100).into(fmpTrackImg)
            fmpForwardBtn.setOnClickListener {
                exoPlayer.seekTo(exoPlayer.currentPosition + 10000L)
            }
            fmpRewindBtn.setOnClickListener {
                exoPlayer.seekTo(exoPlayer.currentPosition - 10000L)
            }

            fmpPlayBtn.setOnClickListener {
                if (exoPlayer.isPlaying) {
                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_play_btn)
                    exoPlayer.pause()
                } else {
                    if (exoPlayer.mediaItemCount > 0) {
                        fmpPlayBtn.setBackgroundResource(R.drawable.ic_pause_btn)
                        exoPlayer.play()
                    }
                }
            }
        }
    }

    private fun configSeekBar() = lifecycleScope.launch(Dispatchers.Main.immediate) {
        val totalDuration = exoPlayer.duration
        var currentPos = 0
        delay(1500)
        binding.fmpSeekBar.max = exoPlayer.duration.toInt()

        binding.fmpSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {
                exoPlayer.seekTo(binding.fmpSeekBar.progress.toLong())
            }
        })
        val endTime = Converter.getMinutes(exoPlayer.duration)
        binding.fmpEndTime.text = endTime

        while (totalDuration < currentPos) {
            delay(500)
            currentPos = exoPlayer.currentPosition.toInt()
            binding.fmpSeekBar.progress = currentPos
        }
    }

    private fun handleCurrentTrackTime() {
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(object : Runnable {
            override fun run() {
                val currTime = Converter.getMinutes(exoPlayer.currentPosition)
                binding.fmpStartTime.text = currTime
                handler?.postDelayed(this, 1000L)
            }
        }, 1000L)
    }

    private fun configVisualization() {
        if (activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) !=
            PackageManager.PERMISSION_DENIED
        ) {
            lifecycleScope.launch(Dispatchers.Main.immediate) {
                val sessionId = exoPlayer.audioSessionId
                if (sessionId != -1) {
                    binding.apply {
                        fmpBv.setAudioSessionId(sessionId)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
        exoPlayer.release()
        handler?.removeCallbacksAndMessages(null)
    }
}