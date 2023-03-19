package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Picket
import com.example.pesaai.service.repository.PicketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllPicketsViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val mRepository = PicketRepository(application)

    private val _picketList = MutableLiveData<List<Picket>>()
    val picketList: LiveData<List<Picket>> = _picketList

    fun load(dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        _picketList.postValue(mRepository.loadPickets())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}