package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.repository.PesagemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllPesagensViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = PesagemRepository(application)

    private val mPesagemList = MutableLiveData<List<Pesagem>>()
    val pesagemList: LiveData<List<Pesagem>> = mPesagemList

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun load(){
        viewModelScope.launch(Dispatchers.IO) {
            mPesagemList.postValue(mRepository.loadPesagens())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}