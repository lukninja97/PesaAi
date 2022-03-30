package com.example.pesaai.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fazenda")
data class Fazenda(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nome: String = "",
    var local: String = "",
    var dono: String = ""
) : Serializable
