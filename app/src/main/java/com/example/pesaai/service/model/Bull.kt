package com.example.pesaai.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bull")
data class Bull(
    @PrimaryKey(autoGenerate = false)
    var brinco: String = "",
    var weight: Float = 0F,
    var arroba: Float = 0F
)
