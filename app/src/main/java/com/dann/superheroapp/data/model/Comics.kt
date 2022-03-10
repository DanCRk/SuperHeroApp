package com.dann.superheroapp.data.model

import java.io.Serializable

data class Comics(
    val items: List<Comic> = emptyList()
):Serializable
