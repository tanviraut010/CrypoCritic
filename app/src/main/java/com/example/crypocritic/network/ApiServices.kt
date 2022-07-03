package com.example.crypocritic.network

import com.example.crypocritic.data.CryptoResponse
import com.example.crypocritic.data.SymbolResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("tickers/24hr")
    fun fetchCryptoList(): Call<CryptoResponse>

    @GET("ticker/24hr")
    fun fetchSymbolDetails(@Query("symbol") symbol: String?): Call<SymbolResponse>
}