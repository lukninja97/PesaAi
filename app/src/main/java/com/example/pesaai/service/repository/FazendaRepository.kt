package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.repository.local.PesaAiDataBase

class FazendaRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).fazendaDAO()

    suspend fun insertFazenda(fazenda: Fazenda) {
        mPesaAiDataBase.insert(fazenda)
    }

    suspend fun deleteFazenda(fazenda: Fazenda) {
        mPesaAiDataBase.delete(fazenda.id)
    }

    suspend fun updateFazenda(fazenda: Fazenda) {
        mPesaAiDataBase.update(fazenda)
    }

    suspend fun getFazenda(id: Int): Fazenda{
        return mPesaAiDataBase.getFazenda(id)
    }

    suspend fun loadFazendas(): List<Fazenda> {
        return mPesaAiDataBase.getAll()
    }
}