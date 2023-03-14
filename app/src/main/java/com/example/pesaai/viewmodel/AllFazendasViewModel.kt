package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.repository.FazendaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllFazendasViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = FazendaRepository(application)

    private val mFarmList = MutableLiveData<List<Farm>>()
    val farmList: LiveData<List<Farm>> = mFarmList

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun load(){
        viewModelScope.launch(Dispatchers.IO) {
            mFarmList.postValue(mRepository.loadFazendas())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}