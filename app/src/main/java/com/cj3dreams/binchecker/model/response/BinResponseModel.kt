package com.cj3dreams.binchecker.model.response

import java.io.Serializable

data class BinResponseModel(
    val bank: Bank?,
    val brand: String?,
    val country: Country?,
    val number: Number?,
    val prepaid: Boolean?,
    val scheme: String?,
    val type: String?,
    var cardNumb: Long? = null
): Serializable