package com.dann.superheroapp.data.model

import java.io.Serializable

data class Hero(
    val name:String,
    val id:Int,
    val thumbnail:Thumbnail
):Serializable
