package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Weight
import com.example.pesaai.service.model.relations.WeightWithBulls
import com.example.pesaai.service.repository.local.PesaAiDataBase

class WeightRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).weightDAO()

    suspend fun insertPesagem(weight: Weight) {
        mPesaAiDataBase.insert(weight)
    }

    suspend fun deletePesagem(weight: Weight) {
        mPesaAiDataBase.delete(weight.id)
    }

    suspend fun updatePesagem(weight: Weight) {
        mPesaAiDataBase.update(weight)
    }

    suspend fun getPesagem(id: Int): Weight{
        return mPesaAiDataBase.getPesagem(id)
    }

    suspend fun loadPesagens(): List<Weight> {
        return mPesaAiDataBase.getAll()
    }

    suspend fun loadBois(id: Int): List<WeightWithBulls>{
        return mPesaAiDataBase.getPesagensWithBois(id)
    }
}