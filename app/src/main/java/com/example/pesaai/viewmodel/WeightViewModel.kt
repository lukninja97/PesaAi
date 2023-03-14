package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Weight
import com.example.pesaai.service.model.relations.WeightWithBulls
import com.example.pesaai.service.repository.FarmRepository
import com.example.pesaai.service.repository.WeightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WeightViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val mWeightRepository = WeightRepository(application)
    private val mFarmRepository = FarmRepository(application)

    private val mWeight = MutableLiveData<Weight>()
    var weight: LiveData<Weight> = mWeight

    private val mBullList = MutableLiveData<List<WeightWithBulls>>()
    val bullList: LiveData<List<WeightWithBulls>> = mBullList

    fun load(id: Int) {
        viewModelScope.launch {
            mWeight.postValue(mWeightRepository.getPesagem(id))
        }
    }

    fun loadBulls(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mBullList.postValue(mWeightRepository.loadBois(id))
        }
    }

    fun getFarm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mFarmRepository.getFazenda(id)
        }
    }

    fun insert(weight: Weight) {
        viewModelScope.launch(Dispatchers.IO) {
            mWeightRepository.insertPesagem(weight)
        }
    }

    fun update(weight: Weight) {
        viewModelScope.launch(Dispatchers.IO) {
            mWeightRepository.updatePesagem(weight)
        }
    }

    fun delete(weight: Weight) {
        viewModelScope.launch(Dispatchers.IO) {
            mWeightRepository.deletePesagem(weight)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}