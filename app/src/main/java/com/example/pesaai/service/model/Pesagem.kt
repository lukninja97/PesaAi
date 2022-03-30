package com.example.pesaai.service.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "pesagem")
data class Pesagem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val fazenda: Int = 0,
    var data: String = "",
    var funcionario: String = "",
    var finalidade: String = "",
    var qntTotal: Int = 0,
    var pesoMedio: Float = 0F,
    var arrobaMedia: Float = 0F,
    val listBois: ArrayList<String>
) : Serializable

class BoisTypeConverter{
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