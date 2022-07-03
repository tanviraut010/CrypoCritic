package com.example.crypocritic.network

import androidx.lifecycle.MutableLiveData
import com.example.crypocritic.data.CryptoResponse
import com.example.crypocritic.data.CryptoResponseItem
import com.example.crypocritic.data.SymbolResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(private val apiInstance: ApiServices) {

    fun mainApiCall(
        liveDataList: MutableLiveData<CryptoResponse?>?
    ) {
        val call: Call<CryptoResponse> = apiInstance.fetchCryptoList()
        call.enqueue(object : Callback<CryptoResponse> {
            override fun onResponse(
                call: Call<CryptoResponse>,
                response: Response<CryptoResponse>
            ) {
                liveDataList?.postValue(response.body())
            }

            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
                liveDataList?.postValue(null)
            }
        })
    }

    fun detailApiCall(
        symbol: String,
        liveDataList: MutableLiveData<SymbolResponse?>?
    ) {
        val call: Call<SymbolResponse> = apiInstance.fetchSymbolDetails(symbol)
        call.enqueue(object : Callback<SymbolResponse> {
            override fun onResponse(
                call: Call<SymbolResponse>,
                response: Response<SymbolResponse>
            ) {
                liveDataList?.postValue(response.body())
            }

            override fun onFailure(call: Call<SymbolResponse>, t: Throwable) {
                liveDataList?.postValue(null)
            }
        })
    }
}