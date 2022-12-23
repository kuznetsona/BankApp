package com.example.bankapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        cordTextView = findViewById(R.id.cordTextView)
        nameBankTextView = findViewById(R.id.nameBankTextView)
        urlBankTextView = findViewById(R.id.urlBankTextView)
        phoneBankTextView = findViewById(R.id.phoneBankTextView)
        prepaidTextView = findViewById(R.id.prepaidTextView)
        searchView = findViewById(R.id.searchView)



        urlBankButton = findViewById(R.id.urlBankButton)

        requestQueue = Volley.newRequestQueue(this)





        var bin = 45717360
        var variable: CharSequence
        //bin = searchView.inputType

        searchView.setOnSearchClickListener {

            variable = searchView.query
            binTextView.text = variable

        }

       // val query: CharSequence = searchView.getQuery()
        //binTextView.text = variable



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