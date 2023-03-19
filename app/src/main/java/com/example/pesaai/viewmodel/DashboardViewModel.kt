package com.example.pesaai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pesaai.service.model.Picket

class DashboardViewModel : ViewModel() {

    private val _picketsList = MutableLiveData<List<Picket>>()
    val picketsList: LiveData<List<Picket>> = _picketsList
    // TODO: Implement the ViewModel
}