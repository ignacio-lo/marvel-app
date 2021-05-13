package com.example.marvelapp.data.model.comic

import com.google.gson.annotations.SerializedName

data class ComicResponse(
    @SerializedName("items")
    val comicList: ArrayList<Comic>
)