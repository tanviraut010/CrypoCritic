package com.example.crypocritic.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypocritic.data.SymbolResponse
import com.example.crypocritic.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {
    var liveDataList: MutableLiveData<SymbolResponse?>? = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<SymbolResponse?>? {
        return liveDataList
    }

    fun fetchCryptoDetail(symbol: String) {
        repository.detailApiCall(symbol, liveDataList)
    }
}