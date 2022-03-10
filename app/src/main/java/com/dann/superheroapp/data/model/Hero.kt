package com.dann.superheroapp.data.model

import java.io.Serializable

data class Hero(
    val name:String = "_",
    val id:Int = 0,
    val thumbnail:Thumbnail = Thumbnail(),
    val description:String = "_",
    val comics:Comics = Comics(),
    val title:String = "_",
    val urls:List<Urls> = emptyList(),
    val images:List<Images> = emptyList()
):Serializable
