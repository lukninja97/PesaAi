package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.model.relations.FazendaWithPesagens

@Dao
interface FazendaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fazenda: Fazenda)

    @Update
    suspend fun update(fazenda: Fazenda): Int

    @Query("DELETE FROM fazenda WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT * FROM fazenda WHERE id = :id")
    suspend fun getFazenda(id: Int): Fazenda

    @Query("SELECT * FROM fazenda")
    suspend fun getAll(): List<Fazenda>

    @Transaction
    @Query("SELECT * FROM fazenda WHERE id = :fazenda")
    suspend fun getFazendaWithPesagens(fazenda: Int): List<FazendaWithPesagens>
}