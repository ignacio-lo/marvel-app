package com.example.marvelapp.data.model.character

import com.example.marvelapp.data.model.base.Thumbnail
import com.example.marvelapp.data.model.comic.Comic
import com.example.marvelapp.data.model.comic.ComicResponse
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("description")
    var description: String = "",

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail,

    @SerializedName("comics")
    var comics: ComicResponse
)

data class CharacterData(
    @SerializedName("results")
    val list: ArrayList<Character>,

    @SerializedName("offset")
    val offset: Int = 0,

    @SerializedName("limit")
    val limit: Int = 0,

    @SerializedName("total")
    val total: Int = 0,

    @SerializedName("count")
    val count: Int = 0
)