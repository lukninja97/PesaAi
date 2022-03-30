package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.repository.FazendaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllFazendasViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = FazendaRepository(application)

    private val mFazendaList = MutableLiveData<List<Fazenda>>()
    val fazendaList: LiveData<List<Fazenda>> = mFazendaList

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun load(){
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaList.postValue(mRepository.loadFazendas())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}