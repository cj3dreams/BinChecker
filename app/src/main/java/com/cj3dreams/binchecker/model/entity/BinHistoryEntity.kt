package com.cj3dreams.binchecker.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "binHistory", indices = [Index(value = ["cardNumb"], unique = true)])
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "cardNumb")
    var cardNumb: Long?,

    @ColumnInfo(name = "brand")
    val brand: String?,
    @ColumnInfo(name = "prepaid")
    val prepaid: Int?,
    @ColumnInfo(name = "scheme")
    val scheme: String?,
    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "bankName")
    val bankName: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "currency")
    val currency: String?,
    @ColumnInfo(name = "emoji")
    val emoji: String?,
    @ColumnInfo(name = "latitude")
    val latitude: Int?,
    @ColumnInfo(name = "longitude")
    val longitude: Int?,
    @ColumnInfo(name = "countryName")
    val countryName: String?,

    @ColumnInfo(name = "length")
    val length: Int?,
    @ColumnInfo(name = "luhn")
    val luhn: Int?


    ):Serializable