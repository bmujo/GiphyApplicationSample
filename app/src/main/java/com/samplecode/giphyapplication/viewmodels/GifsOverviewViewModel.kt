package com.samplecode.giphyapplication.viewmodels

import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.samplecode.giphyapplication.models.Gif
import com.samplecode.giphyapplication.repositories.GifRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GifsOverviewViewModel @Inject constructor(private val repository: GifRepository,
@Assisted state: SavedStateHandle) : ViewModel() {
    private val mutableGifList = MutableLiveData<List<Gif>>()
    public val GifList: LiveData<List<Gif>> get() = mutableGifList

    public val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val listOfGifs = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchGifs(query: String?){
        currentQuery.value = query
    }

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }
}