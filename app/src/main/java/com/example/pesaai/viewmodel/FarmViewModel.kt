package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.repository.FarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FarmViewModel(application: Application) : AndroidViewModel(application) {
    private val mFarmRepository = FarmRepository(application)

    private val mFarm = MutableLiveData<Farm>()
    var farm: LiveData<Farm> = mFarm

    fun load(id: Int) = viewModelScope.launch {
        mFarm.postValue(mFarmRepository.getFazenda(id))
    }

    fun insert(farm: Farm) = viewModelScope.launch(Dispatchers.IO) {
        mFarmRepository.insertFazenda(farm)
    }

    fun update(farm: Farm) {
        viewModelScope.launch(Dispatchers.IO) {
            mFarmRepository.updateFazenda(farm)
        }
    }

    fun delete(farm: Farm) {
        viewModelScope.launch(Dispatchers.IO) {
            mFarmRepository.deleteFazenda(farm)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}