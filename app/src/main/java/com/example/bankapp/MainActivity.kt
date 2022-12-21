package com.example.bankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import com.android.volley.toolbox.JsonArrayRequest
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
    private lateinit var latitudeCountryTextView: TextView
    private lateinit var longitudeCountryTextView: TextView
    private lateinit var nameBankTextView: TextView
    private lateinit var urlBankTextView: TextView
    private lateinit var phoneBankTextView: TextView
    private lateinit var cityBankTextView: TextView
    private lateinit var searchView: SearchView

    lateinit var urlBankButton: Button
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
        latitudeCountryTextView = findViewById(R.id.latitudeСountryTextView)
        longitudeCountryTextView = findViewById(R.id.longitudeCountryTextView)
        nameBankTextView = findViewById(R.id.nameBankTextView)
        urlBankTextView = findViewById(R.id.urlBankTextView)
        phoneBankTextView = findViewById(R.id.phoneBankTextView)
        cityBankTextView = findViewById(R.id. cityBankTextView)
        //searchView = findViewById(R.id.searchView)


        urlBankButton = findViewById(R.id.urlBankButton)

        requestQueue = Volley.newRequestQueue(this)

        var bin = 45717360
        //bin = searchView.inputType


        urlBankButton.setOnClickListener {
            getJSON(bin)

        }

    }

    override fun onStart() {
        super.onStart()


    }

    private fun getJSON(bin: Int){
        //val bin: Int
        val url = "https://lookup.binlist.net/" +
                bin

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { data ->
                try {

                    val dataBIN = DataBIN(
                        data.getJSONObject("number").getString("length").toInt(),
                        data.getJSONObject("number").getString("luhn").toBoolean(),

                        data.getString("scheme").toString(),
                        data.getString("type").toString(),
                        data.getString("brand").toString(),
                        data.getString("prepaid").toBoolean(),

                        data.getJSONObject("country").getString("numeric").toInt(),
                        data.getJSONObject("country").getString("alpha2").toString(),
                        data.getJSONObject("country").getString("name").toString(),
                        data.getJSONObject("country").getString("emoji").toString(),
                        data.getJSONObject("country").getString("currency").toString(),
                        data.getJSONObject("country").getString("latitude").toInt(),
                        data.getJSONObject("country").getString("longitude").toInt(),

                        data.getJSONObject("bank").getString("name").toString(),
                        data.getJSONObject("bank").getString("url").toString(),
                        data.getJSONObject("bank").getString("phone").toString(),
                        data.getJSONObject("bank").getString("city").toString(),
                    )
                    updateDayWeatherUi(dataBIN)
                    //urlBankTextView!!.text = data.toString()
                    //println("url_bank " + dataBIN.url_bank)
                    //Log.d("url_bank", dataBIN.url_bank)


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        requestQueue!!.add(request)
        // urlBankTextView.text = dataBIN.url_bank
    }



    private fun updateDayWeatherUi(dataBIN: DataBIN) {
        //binBankTextView!!.text = dataBIN.url_bank
        schemeTextView!!.text = dataBIN.scheme
        lengthTextView!!.text = dataBIN.length.toString()
        luhnTextView!!.text = dataBIN.luhn.toString()
        typeTextView!!.text = dataBIN.type
        brandTextView!!.text = dataBIN.brand
        numericCountryTextView!!.text = dataBIN.numeric_country.toString()
        alpha2CountryTextView!!.text = dataBIN.alpha2_country
        nameCountryTextView!!.text = dataBIN.name_country
        emojiCountryTextView!!.text = dataBIN.emoji_country
        currencyCountryTextView!!.text = dataBIN.currency_country
        latitudeCountryTextView!!.text = dataBIN.latitude_country.toString()
        longitudeCountryTextView!!.text = dataBIN.longitude_country.toString()
        nameBankTextView!!.text = dataBIN.name_bank
        urlBankTextView!!.text = dataBIN.url_bank
        phoneBankTextView!!.text = dataBIN.phone_bank
        cityBankTextView!!.text = dataBIN.city_bank
    }
}