package com.cj3dreams.binchecker.model.response

import java.io.Serializable

data class Country(
    val alpha2: String?,
    val currency: String?,
    val emoji: String?,
    val latitude: Int?,
    val longitude: Int?,
    val name: String?,
    val numeric: String?
): Serializable