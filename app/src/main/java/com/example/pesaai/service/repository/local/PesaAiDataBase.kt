package com.example.pesaai.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.BoisTypeConverter
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Pesagem

@Database(entities = [Farm::class, Pesagem::class, Boi::class ]
        , version = 1)
@TypeConverters(BoisTypeConverter::class)
abstract class PesaAiDataBase: RoomDatabase() {

    abstract fun fazendaDAO(): FazendaDAO
    abstract fun pesagemDAO(): PesagemDAO
    abstract fun boiDAO(): BoiDAO

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