package com.threeducks.practice.api

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class ResponseData(
        @SerializedName("hits")
        val mainData: List<Contents>
)

data class Contents(
        @SerializedName("previewURL")
        val image: String?,

        @SerializedName("tags")
        val tags: String?
)