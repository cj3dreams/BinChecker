package com.cj3dreams.binchecker.model.response

import java.io.Serializable

data class Number(
    val length: Int?,
    val luhn: Boolean?
): Serializable