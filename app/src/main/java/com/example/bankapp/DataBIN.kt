package com.example.bankapp

data class DataBIN(
    val length: Int,
    val luhn: Boolean,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,

    val numeric_country: Int,
    val alpha2_country: String,
    val name_country: String,
    val emoji_country: String,
    val currency_country: String,
    val latitude_country: Int,
    val longitude_country: Int,

    val name_bank: String,
    val url_bank: String,
    val phone_bank: String,
    val city_bank: String

)
