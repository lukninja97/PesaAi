package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.repository.local.PesaAiDataBase

class BoiRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).boiDAO()

    suspend fun insertBoi(boi: Boi) {
        mPesaAiDataBase.insert(boi)
    }

    suspend fun deleteBoi(boi: Boi) {
        mPesaAiDataBase.delete(boi.brinco)
    }

    suspend fun updateBoi(boi: Boi) {
        mPesaAiDataBase.update(boi)
    }
}