package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.relations.FarmWithWeights

@Dao
interface FarmDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(farm: Farm)

    @Update
    suspend fun update(farm: Farm): Int

    @Query("DELETE FROM farm WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT * FROM farm WHERE id = :id")
    suspend fun getFazenda(id: Int): Farm

    @Query("SELECT * FROM farm")
    suspend fun getAll(): List<Farm>

    @Transaction
    @Query("SELECT * FROM farm WHERE id = :fazenda")
    suspend fun getFazendaWithPesagens(fazenda: Int): List<FarmWithWeights>
}