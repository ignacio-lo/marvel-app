package com.example.marvelapp.domain

import com.example.marvelapp.data.DataSource
import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.data.model.event.EventResponse
import com.example.marvelapp.utils.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getCharacters(offset: Int): Resource<CharacterResponse> {
        return dataSource.getCharacters(offset)
    }

    override suspend fun getCharacterByID(id: Int): Resource<CharacterResponse> {
        return dataSource.getCharacterByID(id)
    }

    override suspend fun getEvents(): Resource<EventResponse> {
        return dataSource.getEvents()
    }
}