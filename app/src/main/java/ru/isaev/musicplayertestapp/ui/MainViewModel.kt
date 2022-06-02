package ru.isaev.musicplayertestapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.isaev.musicplayertestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import ru.isaev.musicplayertestapp.model.SearchMusicResponse
import ru.isaev.musicplayertestapp.repository.MainMusicRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainMusicRepository) : ViewModel() {

    private val _musicSearch: MutableLiveData<Resource<SearchMusicResponse>> = MutableLiveData()
    val musicSearch: LiveData<Resource<SearchMusicResponse>> = _musicSearch

    private val trackList: MutableList<SearchMusicResponse> = mutableListOf()

    fun searchForAllMusic(searchQuery: String) {
        viewModelScope.launch {
            _musicSearch.postValue(Resource.Loading())
            val response = repository.getAllMusicSearchResult(searchQuery)
            musicResponseHandle(response)
        }

    }

    private fun musicResponseHandle(response: Single<SearchMusicResponse>) {
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _musicSearch.postValue(Resource.Success(it))
            }, {
                _musicSearch.postValue(it.message?.let { it1 -> Resource.Error(it1) })
            })
    }

}