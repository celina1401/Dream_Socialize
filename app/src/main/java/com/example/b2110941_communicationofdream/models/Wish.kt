package com.example.b2110941_communicationofdream.models

import com.google.gson.annotations.SerializedName

data class Wish(
    @SerializedName("_id")
    val _id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: User,

    @SerializedName("content")
    val content: String

)
