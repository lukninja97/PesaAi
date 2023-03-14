package com.example.pesaai.service.repository

import android.content.Context
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.model.relations.PesagemWithBois
import com.example.pesaai.service.repository.local.PesaAiDataBase

class PesagemRepository(var context: Context) {
    private val mPesaAiDataBase = PesaAiDataBase.getDataBase(context).pesagemDAO()

    suspend fun insertPesagem(pesagem: Pesagem) {
        mPesaAiDataBase.insert(pesagem)
    }

    suspend fun deletePesagem(pesagem: Pesagem) {
        mPesaAiDataBase.delete(pesagem.id)
    }

    suspend fun updatePesagem(pesagem: Pesagem) {
        mPesaAiDataBase.update(pesagem)
    }

    suspend fun getPesagem(id: Int): Pesagem{
        return mPesaAiDataBase.getPesagem(id)
    }

    suspend fun loadPesagens(): List<Pesagem> {
        return mPesaAiDataBase.getAll()
    }

    suspend fun loadBois(id: Int): List<PesagemWithBois>{
        return mPesaAiDataBase.getPesagensWithBois(id)
    }
}