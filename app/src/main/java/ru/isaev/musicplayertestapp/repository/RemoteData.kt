package ru.isaev.musicplayertestapp.repository

import ru.isaev.musicplayertestapp.network.MusicApi
import javax.inject.Inject

class RemoteData @Inject constructor(private val api: MusicApi) {

    fun getAllMusic(searchQuery: String) = api.getTrackList(searchQuery)
}