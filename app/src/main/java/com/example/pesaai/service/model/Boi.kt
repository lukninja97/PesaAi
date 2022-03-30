package com.example.pesaai.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boi")
data class Boi(
    @PrimaryKey(autoGenerate = false)
    var brinco: String = "",
    var peso: Float = 0F,
    var arroba: Float = 0F
)
