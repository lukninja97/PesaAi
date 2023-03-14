package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.service.model.relations.PesagemWithBois

@Dao
interface PesagemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pesagem: Pesagem)

    @Update
    suspend fun update(pesagem: Pesagem): Int

    @Query("DELETE FROM pesagem WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT * FROM pesagem WHERE id = :id")
    suspend fun getPesagem(id: Int): Pesagem

    @Query("SELECT * FROM pesagem")
    suspend fun getAll(): List<Pesagem>

    @Transaction
    @Query("SELECT * FROM pesagem WHERE id = :id")
    suspend fun getPesagensWithBois(id: Int): List<PesagemWithBois>
    
}