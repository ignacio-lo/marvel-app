package com.example.marvelapp.data

import com.example.marvelapp.BuildConfig
import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.data.model.event.EventResponse
import com.example.marvelapp.utils.Resource
import com.example.marvelapp.utils.RetrofitClient

class DataSource {

    suspend fun getCharacters(offset: Int): Resource<CharacterResponse> {
        return Resource.Success(RetrofitClient.apiService.getCharacters(BuildConfig.API_KEY, BuildConfig.API_HASH, BuildConfig.API_TS, limitCharacters, offset))
    }

    suspend fun getCharacterByID(id: Int): Resource<CharacterResponse> {
        return Resource.Success(RetrofitClient.apiService.getCharacterByID(id, BuildConfig.API_KEY, BuildConfig.API_HASH, BuildConfig.API_TS))
    }

    suspend fun getEvents(): Resource<EventResponse> {
        return Resource.Success(RetrofitClient.apiService.getEvents(BuildConfig.API_KEY, BuildConfig.API_HASH, BuildConfig.API_TS, limitEvent, offset, orderBy))
    }

    companion object {
        val limitCharacters = 15
        val limitEvent = 25
        val offset = 7 //offset to events because the previous events has null Date
        val orderBy = "startDate"
    }
}