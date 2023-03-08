package com.example.pesaai.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.model.BullsTypeConverter
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Weight

@Database(entities = [Farm::class, Weight::class, Bull::class ]
        , version = 1)
@TypeConverters(BullsTypeConverter::class)
abstract class PesaAiDataBase: RoomDatabase() {

    abstract fun fazendaDAO(): FarmDAO
    abstract fun pesagemDAO(): WeightDAO
    abstract fun boiDAO(): BullDAO

    companion object{
        @Volatile
        private lateinit var INSTANCE : PesaAiDataBase

        fun getDataBase(context: Context): PesaAiDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, PesaAiDataBase::class.java, "pesaai_database")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}