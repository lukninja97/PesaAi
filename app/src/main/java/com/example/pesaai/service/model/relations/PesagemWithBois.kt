package com.example.pesaai.service.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Pesagem

data class PesagemWithBois(
    @Embedded val pesagem: Pesagem,
    @Relation(
        parentColumn = "id",
        entityColumn = "brinco"
    )
    val bois: List<Boi>
)
