package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.repository.BoiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BoiViewModel(application: Application) : AndroidViewModel(application) {
    private val mBoiRepository = BoiRepository(application)

    private val mBoi = MutableLiveData<Boi>()
    var boi: LiveData<Boi> = mBoi

    /*fun load(id: Int){
        viewModelScope.launch {
            mBoi.postValue(mBoiRepository.(id))
        }
    }*/

    fun insert(boi: Boi) {
        viewModelScope.launch(Dispatchers.IO) {
            mBoiRepository.insertBoi(boi)
        }
    }

    fun update(boi: Boi) {
        viewModelScope.launch(Dispatchers.IO) {
            mBoiRepository.updateBoi(boi)
        }
    }

    fun delete(boi: Boi){
        viewModelScope.launch(Dispatchers.IO) {
            mBoiRepository.deleteBoi(boi)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}