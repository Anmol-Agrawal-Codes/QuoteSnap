package com.example.quotesnap

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class MainViewModel(val context : Context) : ViewModel() { //context is required for reading data systematically from json file.
    private var quoteList : Array<Quote> = emptyArray()
    private var index = 0;

    init {
        quoteList = loadQuoteFromAsset()
    }

    private fun loadQuoteFromAsset(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        Log.d("Load", "Quote loading.")
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() : Quote{
        if(++ index > quoteList.size){
            return quoteList[-- index]
        }
        return quoteList[index]
    }

    fun previousQuote() : Quote{
        if(-- index < 0){
            return quoteList[++index]
        }
        return quoteList[index]
    }
}