package com.example.wevote

data class DataModel(
    val id : Int,
    val subject: String,
    val opt1name: String,
    val opt1count: Int,
    val opt2name: String,
    val opt2count: Int
)