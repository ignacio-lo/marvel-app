package com.example.marvelapp.ui.events.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.marvelapp.data.model.event.EventResponse
import com.example.marvelapp.domain.Repo
import com.example.marvelapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class EventsViewModel(private val repo: Repo) : ViewModel() {

    fun getEvents(): LiveData<Resource<EventResponse>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getEvents())
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}