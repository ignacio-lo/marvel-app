package com.example.marvelapp.data.model.base

import com.google.gson.annotations.SerializedName

data class Thumbnail (
    @SerializedName("path")
    var path: String = "",

    @SerializedName("extension")
    val extension: String = ""
)