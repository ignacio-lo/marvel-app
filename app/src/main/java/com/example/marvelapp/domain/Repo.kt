package com.example.marvelapp.domain

import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.data.model.event.EventResponse
import com.example.marvelapp.utils.Resource

interface Repo {
    suspend fun getCharacters(offset: Int) : Resource<CharacterResponse>

    suspend fun getCharacterByID(id: Int) : Resource<CharacterResponse>

    suspend fun getEvents() : Resource<EventResponse>
}