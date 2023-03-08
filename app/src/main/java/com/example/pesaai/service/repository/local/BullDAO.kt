package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Bull

@Dao
interface BullDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bull: Bull)

    @Update
    suspend fun update(bull: Bull): Int

    @Query("DELETE FROM bull WHERE brinco = :brinco")
    suspend fun delete(brinco: String): Int
}