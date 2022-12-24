package com.example.bankapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class MainActivity : AppCompatActivity() {

    private lateinit var binTextView: TextView

    private lateinit var schemeTextView: TextView
    private lateinit var lengthTextView: TextView
    private lateinit var luhnTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var brandTextView: TextView
    private lateinit var numericCountryTextView: TextView
    private lateinit var alpha2CountryTextView: TextView
    private lateinit var nameCountryTextView: TextView
    private lateinit var emojiCountryTextView: TextView
    private lateinit var currencyCountryTextView: TextView
    private lateinit var cordTextView: TextView
    private lateinit var nameBankTextView: TextView
    private lateinit var urlBankTextView: TextView
    private lateinit var phoneBankTextView: TextView
    private lateinit var prepaidTextView: TextView

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binTextView = findViewById(R.id.binTextView)
        schemeTextView = findViewById(R.id.schemeTextView)
        lengthTextView = findViewById(R.id.lengthTextView)
        luhnTextView = findViewById(R.id.luhnTextView)
        typeTextView = findViewById(R.id.typeTextView)
        brandTextView = findViewById(R.id.brandTextView)
        numericCountryTextView = findViewById(R.id.numericCountryTextView)
        alpha2CountryTextView = findViewById(R.id.alpha2CountryTextView)
        nameCountryTextView = findViewById(R.id.nameCountryTextView)
        emojiCountryTextView = findViewById(R.id.emojiCountryTextView)
        currencyCountryTextView = findViewById(R.id.currencyСountryTextView)
        cordTextView = findViewById(R.id.cordTextView)
        nameBankTextView = findViewById(R.id.nameBankTextView)
        urlBankTextView = findViewById(R.id.urlBankTextView)
        phoneBankTextView = findViewById(R.id.phoneBankTextView)
        prepaidTextView = findViewById(R.id.prepaidTextView)
        searchView = findViewById(R.id.searchView)





        requestQueue = Volley.newRequestQueue(this)




        //var bin = 55369140
        var bin : Long = 45717360
        var variable: String
        //bin = searchView.inputType


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Добавить новую переменную вместо бин
                bin = searchView.query.toString().toLong()

                while(bin > 100000000){
                    bin /= 10
                }

                if (bin < 10000000){

                }
                getJSON(bin)
                binTextView.text = bin.toString()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //вызовется при изменении ведённого текста
                return true
            }
        })
/*
        searchView.setOnCloseListener() {
            binTextView.text = searchView.query.toString()
        }*/


    }

    override fun onStart() {
        super.onStart()


    }

    private fun getJSON(bin: Long){
        //val bin: Int
        val url = "https://lookup.binlist.net/$bin"

        var length = 0
        var luhn = false
        var scheme = "?"
        var type = "?"
        var brand = "?"
        var prepaid = false

        var numeric = 0
        var alpha2 = "?"
        var name = "?"
        var emoji = "?"
        var currency = "?"
        var latitude = 0
        var longitude = 0

        var name_bank = "?"
        var url_bank = "?"
        var phone_bank = "?"
        var city_bank = "?"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { data ->
                try {
                    // Сделать красиво все
                    if(data.getJSONObject("number").has("length")){
                        length = data.getJSONObject("number").getString("length").toInt()
                    }
                    if(data.getJSONObject("number").has("luhn")){
                        luhn = data.getJSONObject("number").getString("luhn").toBoolean()
                    }
                    if(data.has("scheme")){
                        scheme = data.getString("scheme").toString()
                    }
                    if(data.has("type")){
                        type = data.getString("type").toString()
                    }
                    if(data.has("brand")){
                        brand =data.getString("brand").toString()
                    }
                    if(data.has("prepaid")){
                        prepaid = data.getString("prepaid").toBoolean()
                    }
                    if(data.getJSONObject("country").has("numeric")){
                        numeric = data.getJSONObject("country").getString("numeric").toInt()
                    }
                    if(data.getJSONObject("country").has("alpha2")){
                        alpha2 = data.getJSONObject("country").getString("alpha2").toString()
                    }
                    if(data.getJSONObject("country").has("name")){
                        name = data.getJSONObject("country").getString("name").toString()
                    }
                    if(data.getJSONObject("country").has("emoji")){
                        emoji = data.getJSONObject("country").getString("emoji").toString()
                    }
                    if(data.getJSONObject("country").has("currency")){
                        currency = data.getJSONObject("country").getString("currency").toString()
                    }
                    if(data.getJSONObject("country").has("latitude")){
                        latitude = data.getJSONObject("country").getString("latitude").toInt()
                    }
                    if(data.getJSONObject("country").has("longitude")){
                        longitude = data.getJSONObject("country").getString("longitude").toInt()
                    }
                    if(data.getJSONObject("bank").has("name")){
                        name_bank = data.getJSONObject("bank").getString("name").toString()
                    }
                    if(data.getJSONObject("bank").has("url")){
                        url_bank = data.getJSONObject("bank").getString("url").toString()
                    }
                    if(data.getJSONObject("bank").has("phone")){
                        phone_bank = data.getJSONObject("bank").getString("phone").toString()
                    }
                    if(data.getJSONObject("bank").has("city")){
                        city_bank = data.getJSONObject("bank").getString("city").toString()
                    }

                    val dataBIN = DataBIN(
                        length, luhn, scheme, type, brand, prepaid,
                        numeric, alpha2, name, emoji, currency, latitude, longitude,
                        name_bank, url_bank, phone_bank, city_bank,
                    )
                    updateDayWeatherUi(dataBIN)


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        requestQueue!!.add(request)
    }



    private fun updateDayWeatherUi(dataBIN: DataBIN) {
        //binBankTextView!!.text = dataBIN.url_bank
        schemeTextView!!.text = dataBIN.scheme.capitalize()
        lengthTextView!!.text = dataBIN.length.toString()
        typeTextView!!.text = dataBIN.type.capitalize()
        brandTextView!!.text = dataBIN.brand
        numericCountryTextView!!.text = dataBIN.numeric_country.toString()
        alpha2CountryTextView!!.text = dataBIN.alpha2_country + " "
        nameCountryTextView!!.text = " "+ dataBIN.name_country
        emojiCountryTextView!!.text = dataBIN.emoji_country
        currencyCountryTextView!!.text = dataBIN.currency_country
        cordTextView!!.text = "(lat: " + dataBIN.latitude_country +
                ", lon: " + dataBIN.longitude_country + ")"

        nameBankTextView!!.text = dataBIN.name_bank + ", " + dataBIN.city_bank
        urlBankTextView!!.text = dataBIN.url_bank
        phoneBankTextView!!.text = dataBIN.phone_bank
        if(dataBIN.prepaid){
            prepaidTextView!!.text = "Yes"
        } else prepaidTextView!!.text = "No"
        if(dataBIN.luhn){
            luhnTextView!!.text = "Yes"
        } else luhnTextView!!.text = "No"

    }
}



