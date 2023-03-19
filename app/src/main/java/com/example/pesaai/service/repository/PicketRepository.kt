package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Picket
import com.example.pesaai.service.repository.local.PesaAiDataBase

class PicketRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).picketDAO()

    suspend fun insertPicket(picket: Picket) {
        mPesaAiDataBase.insert(picket)
    }

    suspend fun deletePicket(picket: Picket) {
        mPesaAiDataBase.delete(picket.id)
    }

    suspend fun updatePicket(picket: Picket) {
        mPesaAiDataBase.update(picket)
    }

    suspend fun getPicket(id: Long): Picket {
        return mPesaAiDataBase.getPicket(id)
    }

    suspend fun loadPickets(): List<Picket> {
        return mPesaAiDataBase.getAll()
    }
}