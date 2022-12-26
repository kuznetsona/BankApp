package com.example.bankapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
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
    private lateinit var dataBIN: DataBIN
    private lateinit var searchView: SearchView

    private var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binTextView = findViewById(R.id.binTextView)
        searchView = findViewById(R.id.searchView)
        requestQueue = Volley.newRequestQueue(this)

        var bin : Long
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                bin = searchView.query.toString().toLong()

                while(bin > 100000000){
                    bin /= 10
                }
                getJSON(bin)
                binTextView.text = (bin / 10000).toString() + " " + (bin % 10000)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

    }


    private fun getJSON(bin: Long){
        val url = "https://lookup.binlist.net/$bin"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { data ->
                try {
                    val dataCountry = data.getJSONObject("country")
                    val dataBank = data.getJSONObject("bank")

                    dataBIN = DataBIN(
                        if(data.getJSONObject("number").has("length")){
                            data.getJSONObject("number").getString("length").toInt()
                        } else 0,
                        if(data.getJSONObject("number").has("luhn")){
                            data.getJSONObject("number").getString("luhn").toString()
                        } else "-",
                        if(data.has("scheme")){
                            data.getString("scheme").toString()
                        } else "-",
                        if(data.has("type")){
                            data.getString("type").toString()
                        }else "-",
                        if(data.has("brand")){
                            data.getString("brand").toString()
                        }else "-",
                        if(data.has("prepaid")){
                            data.getString("prepaid").toString()
                        }else "-",
                        if(dataCountry.has("numeric")){
                            dataCountry.getString("numeric").toInt()
                        }else 0,
                        if(dataCountry.has("alpha2")){
                            dataCountry.getString("alpha2").toString()
                        }else "-",
                        if(dataCountry.has("name")){
                            dataCountry.getString("name").toString()
                        }else "-",
                        if(dataCountry.has("emoji")){
                            dataCountry.getString("emoji").toString()
                        }else "-",
                        if(dataCountry.has("currency")){
                            dataCountry.getString("currency").toString()
                        }else "-",
                        if(dataCountry.has("latitude")){
                            dataCountry.getString("latitude").toInt()
                        }else 0,
                        if(dataCountry.has("longitude")){
                            dataCountry.getString("longitude").toDouble()
                        }else 0.0,
                        if(dataBank.has("name")){
                            dataBank.getString("name").toString()
                        }else "-",
                        if(dataBank.has("url")){
                            dataBank.getString("url").toString()
                        }else "-",
                        if(dataBank.has("phone")){
                            dataBank.getString("phone").toString()
                        }else "-",
                        if(dataBank.has("city")){
                            dataBank.getString("city").toString()
                        }else "-",
                    )
                    updateDayWeatherUi(dataBIN)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        requestQueue!!.add(request)
    }

    private fun updateDayWeatherUi(dataBIN: DataBIN) {

        val schemeTextView = findViewById<View>(R.id.schemeTextView) as TextView
        val lengthTextView = findViewById<View>(R.id.lengthTextView) as TextView
        val luhnTextView = findViewById<View>(R.id.luhnTextView) as TextView
        val typeTextView = findViewById<View>(R.id.typeTextView) as TextView
        val brandTextView = findViewById<View>(R.id.brandTextView) as TextView
        val numericCountryTextView = findViewById<View>(R.id.numericCountryTextView) as TextView
        val alpha2CountryTextView = findViewById<View>(R.id.alpha2CountryTextView) as TextView
        val nameCountryTextView = findViewById<View>(R.id.nameCountryTextView) as TextView
        val emojiCountryTextView = findViewById<View>(R.id.emojiCountryTextView) as TextView
        val currencyCountryTextView = findViewById<View>(R.id.currencyСountryTextView) as TextView
        val cordTextView = findViewById<View>(R.id.cordTextView) as TextView
        val nameBankTextView = findViewById<View>(R.id.nameBankTextView) as TextView
        val urlBankTextView = findViewById<View>(R.id.urlBankTextView) as TextView
        val phoneBankTextView = findViewById<View>(R.id.phoneBankTextView) as TextView
        val prepaidTextView = findViewById<View>(R.id.prepaidTextView) as TextView

        schemeTextView!!.text = dataBIN.scheme.capitalize()
        if (dataBIN.length == 0) {
            lengthTextView!!.text = "-"
        } else  lengthTextView!!.text = dataBIN.length.toString()

        typeTextView!!.text = dataBIN.type.capitalize()
        brandTextView!!.text = dataBIN.brand

        if (dataBIN.length == 0) {
            numericCountryTextView!!.text = "-"
        } else  numericCountryTextView!!.text = dataBIN.numeric_country.toString()

        if (dataBIN.length == 0) {
            alpha2CountryTextView!!.text = " "
        } else  alpha2CountryTextView!!.text = dataBIN.alpha2_country + " "

        nameCountryTextView!!.text = " "+ dataBIN.name_country
        emojiCountryTextView!!.text = dataBIN.emoji_country
        currencyCountryTextView!!.text = dataBIN.currency_country

        val geo = "Geo: " + dataBIN.latitude_country + ".0," + dataBIN.longitude_country + ".0"
        val content = SpannableString(geo)
        content.setSpan(UnderlineSpan(), 0, geo.length, 0)
        cordTextView.text = content

        nameBankTextView!!.text = dataBIN.name_bank + ", " + dataBIN.city_bank
        urlBankTextView!!.text = dataBIN.url_bank
        phoneBankTextView!!.text = dataBIN.phone_bank

        if(dataBIN.prepaid == "true"){
            prepaidTextView!!.text = "Yes"
        } else if (dataBIN.prepaid == "false") {
            prepaidTextView!!.text = "No"
        } else prepaidTextView!!.text = "-"

        if(dataBIN.luhn == "true"){
            luhnTextView!!.text = "Yes"
        } else if (dataBIN.luhn == "false"){
            luhnTextView!!.text = "No"
        } else luhnTextView!!.text = "-"

    }

    fun openMap(view: View) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:" + dataBIN.latitude_country + ".0," + dataBIN.longitude_country + ".0")
        )
        startActivity(intent)
    }
}



