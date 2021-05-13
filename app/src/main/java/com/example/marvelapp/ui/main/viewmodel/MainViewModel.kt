package com.example.marvelapp.ui.main.viewmodel

import androidx.lifecycle.*
import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.domain.Repo
import com.example.marvelapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repo: Repo) : ViewModel() {

    var offset = 0
    private val limitItemPage = 15

    fun getCharacters(nextPage: Boolean): LiveData<Resource<CharacterResponse>> {

        if (nextPage) {
            offset += limitItemPage
        } else {
            offset = 0
        }

        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getCharacters(offset))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}