package com.example.b2110941_communicationofdream.models

import com.google.gson.annotations.SerializedName

class RequestAddWish (
    val idUser: String,
    @SerializedName("name")
    val fullName: String,
    val content: String
)