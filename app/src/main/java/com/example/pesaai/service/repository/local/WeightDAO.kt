package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Weight
import com.example.pesaai.service.model.relations.WeightWithBulls

@Dao
interface WeightDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weight: Weight)

    @Update
    suspend fun update(weight: Weight): Int

    @Query("DELETE FROM weight WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT * FROM weight WHERE id = :id")
    suspend fun getPesagem(id: Int): Weight

    @Query("SELECT * FROM weight")
    suspend fun getAll(): List<Weight>

    @Transaction
    @Query("SELECT * FROM weight WHERE id = :id")
    suspend fun getPesagensWithBois(id: Int): List<WeightWithBulls>
    
}