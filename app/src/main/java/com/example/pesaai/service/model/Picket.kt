package com.example.pesaai.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picket")
data class Picket(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var idFarm: Long = 0L,
    var name: String = "",
    var description: String = "",
)