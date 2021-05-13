package com.example.marvelapp.data

import com.example.marvelapp.data.model.character.CharacterResponse
import com.example.marvelapp.data.model.event.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(@Query("apikey") apiKey: String, @Query("hash") hash: String, @Query("ts") ts: Int, @Query("limit") limit: Int, @Query("offset") offset: Int): CharacterResponse

    @GET("characters/{character_id}")
    suspend fun getCharacterByID(@Path("character_id") id: Int, @Query("apikey") apiKey: String, @Query("hash") hash: String, @Query("ts") ts: Int): CharacterResponse

    @GET("events")
    suspend fun getEvents(@Query("apikey") apiKey: String, @Query("hash") hash: String, @Query("ts") ts: Int, @Query("limit") limit: Int, @Query("offset") offset: Int, @Query("orderBy") orderBy: String): EventResponse
}