package com.example.pesaai.service.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "weight")
data class Weight(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val farm: Int = 0,
    var date: String = "",
    var employee: String = "",
    var finality: String = "",
    var qntTotal: Int = 0,
    var middleWeight: Float = 0F,
    var arrobaMedia: Float = 0F,
    val listBulls: ArrayList<String>
) : Serializable

class BullsTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{

        val listType =object :TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {

        return Gson().toJson(list)
    }
}