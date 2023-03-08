package com.example.pesaai.service.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Weight

data class FarmWithWeights(
    @Embedded val farm: Farm,
    @Relation(
        parentColumn = "id",
        entityColumn = "farm"
    )
    val weights: List<Weight>
)
