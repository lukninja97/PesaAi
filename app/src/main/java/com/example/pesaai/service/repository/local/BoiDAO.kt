package com.example.pesaai.service.repository.local

import androidx.room.*
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Fazenda

@Dao
interface BoiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(boi: Boi)

    @Update
    suspend fun update(boi: Boi): Int

    @Query("DELETE FROM boi WHERE brinco = :brinco")
    suspend fun delete(brinco: String): Int
}