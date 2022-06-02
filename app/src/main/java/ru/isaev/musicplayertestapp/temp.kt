package ru.isaev.musicplayertestapp

//      setupMediaPlayer(trackInstance)
////        configSeekBar()
////        configForwardButton()
////        configVisualizer()
//
//    }
//
//    private fun configVisualizer() {
//        val sessionId = mediaPlayer.audioSessionId
//        if (sessionId!= -1){
//            binding.apply {
//                fmpBv.setAudioSessionId(sessionId)
//            }
//        }
//    }
//
//    private fun configForwardButton() {
//        binding.apply {
//            fmpForwardBtn.setOnClickListener {
//                mediaPlayer.seekTo(mediaPlayer.currentPosition + 10000)
//            }
//            fmpRewindBtn.setOnClickListener {
//                mediaPlayer.seekTo(mediaPlayer.currentPosition - 10000)
//            }
//        }
//    }
//
//    private fun setupMediaPlayer(track: Result) {
//        binding.apply {
//            fmpArtistTitle.text = track.artistName
//            fmpSongTitle.text = track.trackName
//            _mediaPlayer = MediaPlayer()
//            mediaPlayer.reset()
//            mediaPlayer.setDataSource("https://cdndl.zaycev.net/track/24690653/7Djn5sdCoiMs2jhLYd62Foy7YCTzvc1C4bhHef6QmD6FMB12xbi6z1GwNZk1RjDRoZmuJzt2XHzun2Hp4zKcVqfWx7Us9oRyCbrQXSxhfbRPEawNuHftqqjMofHepXCVBCcvgrhNtLAGPzZS4k8AAdMJ2CDnUd97EK3sma9xBTrsccJToGfgUVnynEQD5NfF5zqLxpqYYxetDKpoQhDtE14ohitnbmYjg4yoKfdYNEMBTZS5DwnasFDDyxQz6DCARYuWkswpKzpWCRWeAkeTjW5HYhuixqKZuE9S5DBKqk7xDsg25ftmqcChaREDpH4n4NnRd7FfDw1cKb7prppcBXe568FAMZubTiSYtPi4ppbnJGRXBA2Y")
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            mediaPlayer.prepareAsync()
//            mediaPlayer.setOnPreparedListener {
//                mediaPlayer.start()
//            }
//            fmpPlayBtn.setOnClickListener {
//                if (mediaPlayer.isPlaying) {
//                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_play_btn)
//                    mediaPlayer.pause()
//                } else {
//                    fmpPlayBtn.setBackgroundResource(R.drawable.ic_pause_btn)
//                    mediaPlayer.start()
//                }
//            }
//
//        }
//    }
//
//    private fun configSeekBar() = lifecycleScope.launch(Dispatchers.Main.immediate) {
//        binding.apply {
//            fmpSeekBar.max = mediaPlayer.duration
//            val totalDuration = mediaPlayer.duration
//            var currentPos = 0
//
//            while (totalDuration < currentPos) {
//                delay(500)
//                currentPos = mediaPlayer.currentPosition
//                fmpSeekBar.progress = currentPos
//            }
//
//            fmpSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
//                override fun onStartTrackingTouch(p0: SeekBar?) {}
//
//                override fun onStopTrackingTouch(p0: SeekBar?) {
//                    mediaPlayer.seekTo(fmpSeekBar.progress)
//                }
//            })
//            fmpEndTime.text = getMusicTime(mediaPlayer.duration)
////            val handler = Handler(Looper.getMainLooper())
////            handler.postDelayed({
////                kotlin.run {
////                    fmpStartTime.text = getMusicTime(mediaPlayer.currentPosition)
////                    handler.postDelayed(run,1000)
////                }
////
////            },1000)
//        }
//    }
//
//    private fun getMusicTime(time: Int): String {
//        var minute = (time / 1000 / 60).toString()
//        var second = (time / 1000 % 60).toString()
//
//        if (second.toInt() < 10) {
//            second = "0$second"
//        }
//        return ("$minute:$second")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }