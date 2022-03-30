package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.repository.FazendaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FazendaViewModel(application: Application) : AndroidViewModel(application) {
    private val mFazendaRepository = FazendaRepository(application)

    private val mFazenda = MutableLiveData<Fazenda>()
    var fazenda: LiveData<Fazenda> = mFazenda

    fun load(id: Int){
        viewModelScope.launch {
            mFazenda.postValue(mFazendaRepository.getFazenda(id))
        }
    }

    fun insert(fazenda: Fazenda) {
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.insertFazenda(fazenda)
        }
    }

    fun update(fazenda: Fazenda) {
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.updateFazenda(fazenda)
        }
    }

    fun delete(fazenda: Fazenda){
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.deleteFazenda(fazenda)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}