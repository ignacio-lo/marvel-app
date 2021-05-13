package com.example.marvelapp.data.model.event

import com.google.gson.annotations.SerializedName

data class EventResponse (
    @SerializedName("data")
    val data: EventData
)