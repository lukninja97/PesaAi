package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.repository.FarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllFarmsViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = FarmRepository(application)

    private val _farmList = MutableLiveData<List<Farm>>()
    val farmList: LiveData<List<Farm>> = _farmList

    fun load(dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        _farmList.postValue(mRepository.loadFazendas())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}