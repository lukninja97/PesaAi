package com.example.pesaai.service.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.model.Weight

data class WeightWithBulls(
    @Embedded val weight: Weight,
    @Relation(
        parentColumn = "id",
        entityColumn = "brinco"
    )
    val bulls: List<Bull>
)
