package br.com.atividade1n1.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pizza::class], version = 1)
abstract class PizzaDatabase: RoomDatabase() {

    abstract fun PizzaDao(): PizzaDao

    companion object {
        private var database: PizzaDatabase? = null
        private val DATABASE = "PizzaDB"

        fun getInstance(context: Context): PizzaDatabase {
            if (database == null)
                database = criaBanco(context)

            return database!!
        }

        private fun criaBanco(context: Context): PizzaDatabase {
            return Room.databaseBuilder(context, PizzaDatabase::class.java, DATABASE)
                .allowMainThreadQueries()
                .build()
        }
    }
}