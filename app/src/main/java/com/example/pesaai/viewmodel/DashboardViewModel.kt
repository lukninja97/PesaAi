package com.example.pesaai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pesaai.service.model.Weight

class DashboardViewModel : ViewModel() {

    private val _picketsList = MutableLiveData<List<Weight>>()
    val picketsList: LiveData<List<Weight>> = _picketsList
    // TODO: Implement the ViewModel
}