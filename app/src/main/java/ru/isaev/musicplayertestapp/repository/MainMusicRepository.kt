package ru.isaev.musicplayertestapp.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainMusicRepository @Inject constructor(private val remote: RemoteData) {

//    Remote
    fun getAllMusicSearchResult(searchQuery: String) = remote.getAllMusic(searchQuery)
}