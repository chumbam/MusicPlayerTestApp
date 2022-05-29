package ru.isaev.musicplayertestapp.model

data class SearchMusicResponse(
    val resultCount: Int,
    val results: List<Result>
)