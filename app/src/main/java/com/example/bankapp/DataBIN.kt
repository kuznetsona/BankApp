package com.example.bankapp

data class DataBIN(
    val length: Int = 0,
    val luhn: Boolean = false,
    val scheme: String = "",
    val type: String = "",
    val brand: String = "",
    val prepaid: Boolean  = false,

    val numeric_country: Int = 0,
    val alpha2_country: String = "",
    val name_country: String = "",
    val emoji_country: String = "",
    val currency_country: String = "",
    val latitude_country: Int = 0,
    val longitude_country: Int = 0,

    val name_bank: String = "",
    val url_bank: String = "",
    val phone_bank: String = "",
    val city_bank: String = ""

)
