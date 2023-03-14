package com.example.pesaai.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.model.relations.PesagemWithBois
import com.example.pesaai.service.repository.FazendaRepository
import com.example.pesaai.service.repository.PesagemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PesagemViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val mPesagemRepository = PesagemRepository(application)
    private val mFazendaRepository = FazendaRepository(application)

    private val mPesagem = MutableLiveData<Pesagem>()
    var pesagem: LiveData<Pesagem> = mPesagem

    private val mBoiList = MutableLiveData<List<PesagemWithBois>>()
    val boiList: LiveData<List<PesagemWithBois>> = mBoiList

    fun load(id: Int){
        viewModelScope.launch {
            mPesagem.postValue(mPesagemRepository.getPesagem(id))
        }
    }

    fun loadBois(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            mBoiList.postValue(mPesagemRepository.loadBois(id))
        }
    }

    fun getFazenda(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            mFazendaRepository.getFazenda(id)
        }
    }

    fun insert(pesagem: Pesagem) {
        viewModelScope.launch(Dispatchers.IO) {
            mPesagemRepository.insertPesagem(pesagem)
        }
    }

    fun update(pesagem: Pesagem) {
        viewModelScope.launch(Dispatchers.IO) {
            mPesagemRepository.updatePesagem(pesagem)
        }
    }

    fun delete(pesagem: Pesagem){
        viewModelScope.launch(Dispatchers.IO) {
            mPesagemRepository.deletePesagem(pesagem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}