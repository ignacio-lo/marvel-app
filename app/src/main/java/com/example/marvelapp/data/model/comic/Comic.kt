package com.example.marvelapp.data.model.comic

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("name")
    val name: String
)