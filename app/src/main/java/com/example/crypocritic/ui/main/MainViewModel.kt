package com.example.crypocritic.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypocritic.data.CryptoResponse
import com.example.crypocritic.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {
    var liveDataList: MutableLiveData<CryptoResponse?>? = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<CryptoResponse?>? {
        return liveDataList
    }

    fun fetchCryptoResponseList() {
        repository.mainApiCall(liveDataList)
    }
}