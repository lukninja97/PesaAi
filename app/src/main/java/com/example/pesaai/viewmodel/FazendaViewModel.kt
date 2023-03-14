package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.repository.FazendaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FazendaViewModel(application: Application) : AndroidViewModel(application) {
    private val mFazendaRepository = FazendaRepository(application)

    private val mFarm = MutableLiveData<Farm>()
    var farm: LiveData<Farm> = mFarm

    fun load(id: Int){
        viewModelScope.launch {
            mFarm.postValue(mFazendaRepository.getFazenda(id))
        }
    }

    fun insert(farm: Farm) {
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.insertFazenda(farm)
        }
    }

    fun update(farm: Farm) {
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.updateFazenda(farm)
        }
    }

    fun delete(farm: Farm){
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.deleteFazenda(farm)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}