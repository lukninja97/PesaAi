package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.repository.BullRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BullViewModel(application: Application) : AndroidViewModel(application) {
    private val mBullRepository = BullRepository(application)

    private val mBull = MutableLiveData<Bull>()
    var bull: LiveData<Bull> = mBull

    fun insert(bull: Bull) {
        viewModelScope.launch(Dispatchers.IO) {
            mBullRepository.insertBoi(bull)
        }
    }

    fun update(bull: Bull) {
        viewModelScope.launch(Dispatchers.IO) {
            mBullRepository.updateBoi(bull)
        }
    }

    fun delete(bull: Bull){
        viewModelScope.launch(Dispatchers.IO) {
            mBullRepository.deleteBoi(bull)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}