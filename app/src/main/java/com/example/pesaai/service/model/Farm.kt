package com.example.pesaai.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "farm")
data class Farm(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var local: String = "",
    var proprietor: String = ""
) : Serializable
