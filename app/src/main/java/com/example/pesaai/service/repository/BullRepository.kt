package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.repository.local.PesaAiDataBase

class BullRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).bullDAO()

    suspend fun insertBoi(bull: Bull) {
        mPesaAiDataBase.insert(bull)
    }

    suspend fun deleteBoi(bull: Bull) {
        mPesaAiDataBase.delete(bull.brinco)
    }

    suspend fun updateBoi(bull: Bull) {
        mPesaAiDataBase.update(bull)
    }
}