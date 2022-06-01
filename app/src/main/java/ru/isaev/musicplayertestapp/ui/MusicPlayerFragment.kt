package ru.isaev.musicplayertestapp.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import retrofit2.http.Url
import ru.isaev.musicplayertestapp.R
import ru.isaev.musicplayertestapp.databinding.FragmentMusicPlayerBinding
import java.io.File
import java.net.URL

class MusicPlayerFragment : Fragment() {

    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val args: MusicPlayerFragmentArgs by navArgs()
    lateinit var exoPlayer: ExoPlayer
//    private var _mediaPlayer: MediaPlayer? = null
//    private val mediaPlayer
//    get() = checkNotNull(_mediaPlayer) {
//            "Media Player is not initialized"
//        }
    private val song: File? = null

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
        Log.e("TAG", trackInstance.toString())
//        if(_mediaPlayer!=null){
//            mediaPlayer.stop()
//            mediaPlayer.release()
//        }
        exoPlayer = ExoPlayer.Builder(context!!).build()
        exoPlayer.repeatMode = (Player.REPEAT_MODE_ALL)
        binding.apply {
            fmpForwardBtn.setOnClickListener {
                exoPlayer.seekTo(exoPlayer.currentPosition + 10000L)
            }
            fmpRewindBtn.setOnClickListener {
                exoPlayer.seekTo(exoPlayer.currentPosition - 10000L)
            }

            fmpPlayBtn.setOnClickListener {
                if (exoPlayer.isPlaying){
                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_play_btn)
                    exoPlayer.pause()
                } else {
                    if (exoPlayer.mediaItemCount > 0){
                        fmpPlayBtn.setBackgroundResource(R.drawable.ic_pause_btn)
                        exoPlayer.play()
                    }
            }
            }
        }

        val firsItem = MediaItem.fromUri(Uri.parse(args.song.previewUrl))
        exoPlayer.setMediaItem(firsItem)
        exoPlayer.prepare()
        exoPlayer.play()



//        binding.apply {
//            fmpArtistTitle.text = trackInstance.artistName
//            fmpSongTitle.text = trackInstance.trackName
//            _mediaPlayer = MediaPlayer.create(context, Uri.parse(args.song.previewUrl))
//            mediaPlayer.reset()
//            mediaPlayer.setDataSource(args.song.previewUrl)
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            mediaPlayer.prepareAsync()
//            mediaPlayer.setOnPreparedListener {
//                mediaPlayer.start()
//            }
//            fmpPlayBtn.setOnClickListener {
//                if (mediaPlayer.isPlaying){
//                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_play_btn)
//                    mediaPlayer.pause()
//                } else{
//                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_pause_btn)
//                    mediaPlayer.start()
//                }
//            }
//        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayer.isPlaying){
            exoPlayer.stop()
        }
        exoPlayer.release()
    }
}