package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Weight
import com.example.pesaai.service.repository.WeightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllWeightsViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = WeightRepository(application)

    private val mWeightList = MutableLiveData<List<Weight>>()
    val weightList: LiveData<List<Weight>> = mWeightList

    fun load(){
        viewModelScope.launch(Dispatchers.IO) {
            mWeightList.postValue(mRepository.loadPesagens())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}