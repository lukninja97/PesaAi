package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.repository.local.PesaAiDataBase

class FarmRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).fazendaDAO()

    suspend fun insertFazenda(farm: Farm) {
        mPesaAiDataBase.insert(farm)
    }

    suspend fun deleteFazenda(farm: Farm) {
        mPesaAiDataBase.delete(farm.id)
    }

    suspend fun updateFazenda(farm: Farm) {
        mPesaAiDataBase.update(farm)
    }

    suspend fun getFazenda(id: Int): Farm{
        return mPesaAiDataBase.getFazenda(id)
    }

    suspend fun loadFazendas(): List<Farm> {
        return mPesaAiDataBase.getAll()
    }
}