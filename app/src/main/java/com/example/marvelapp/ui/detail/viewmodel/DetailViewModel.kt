package com.example.marvelapp.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.data.model.event.EventResponse
import com.example.marvelapp.domain.Repo
import com.example.marvelapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val repo: Repo) : ViewModel() {

    fun getCharacterByID(id: Int): LiveData<Resource<CharacterResponse>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getCharacterByID(id))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}