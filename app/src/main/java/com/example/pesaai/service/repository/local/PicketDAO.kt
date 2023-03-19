package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Picket

@Dao
interface PicketDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(picket: Picket)

    @Update
    suspend fun update(picket: Picket): Int

    @Query("DELETE FROM picket WHERE id = :id")
    suspend fun delete(id: Long): Int

    @Query("SELECT * FROM picket WHERE id = :id")
    suspend fun getPicket(id: Long): Picket

    @Query("SELECT * FROM picket")
    suspend fun getAll(): List<Picket>
}