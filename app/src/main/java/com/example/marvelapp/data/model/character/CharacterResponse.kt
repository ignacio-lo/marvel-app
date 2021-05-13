package com.example.marvelapp.data.model.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
    @SerializedName("data")
    val data: CharacterData
)