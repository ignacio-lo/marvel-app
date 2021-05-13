package com.example.marvelapp.data.model.event

import com.example.marvelapp.data.model.base.Thumbnail
import com.example.marvelapp.data.model.comic.ComicResponse
import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class Event(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("start")
    var start: String = "",

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail,

    @SerializedName("comics")
    var comics: ComicResponse
)

data class EventData(
    @SerializedName("results")
    val list: ArrayList<Event>,

    @SerializedName("offset")
    val offset: Int = 0,

    @SerializedName("limit")
    val limit: Int = 0,

    @SerializedName("total")
    val total: Int = 0,

    @SerializedName("count")
    val count: Int = 0
)