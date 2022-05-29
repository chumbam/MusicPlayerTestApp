package ru.isaev.musicplayertestapp.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.isaev.musicplayertestapp.model.SearchMusicResponse

interface MusicApi {
    @GET("search")
    fun getTrackList(
        @Query("term") trackName: String
    ): Single<SearchMusicResponse>
}