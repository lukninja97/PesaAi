package com.example.pesaai.service.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.service.model.Pesagem

data class FazendaWithPesagens(
    @Embedded val fazenda: Fazenda,
    @Relation(
        parentColumn = "id",
        entityColumn = "fazenda"
    )
    val pesagens: List<Pesagem>
)
