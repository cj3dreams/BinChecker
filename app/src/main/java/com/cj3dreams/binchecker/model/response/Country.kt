package com.cj3dreams.binchecker.model.response

import java.io.Serializable

data class Country(
    val currency: String?,
    val emoji: String?,
    val latitude: Int?,
    val longitude: Int?,
    val name: String?,
): Serializable