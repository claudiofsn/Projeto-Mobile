package br.com.atividade1n1.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PizzaDao {

    @Insert
    fun save(pizza: Pizza)

    @Delete
    fun remove(pizza: Pizza)

    @Query("SELECT * FROM Pizza")
    fun selectAll(): List<Pizza>

    @Query("SELECT * FROM Pizza ORDER BY LOWER(Pizza.nome)")
    fun selectPizzaNomeAsc(): List<Pizza>

    @Query("SELECT * FROM Pizza ORDER BY LOWER(Pizza.nome) DESC")
    fun selectPizzaNomeDesc(): List<Pizza>

    @Query("SELECT * FROM Pizza ORDER BY Pizza.valor")
    fun selectPizzaValorAsc(): List<Pizza>

    @Query("SELECT * FROM Pizza ORDER BY Pizza.valor DESC")
    fun selectPizzaValorDesc(): List<Pizza>

    @Query("SELECT * FROM Pizza WHERE Pizza.Id = :id")
    fun selectPizzaById(id: Int): Pizza
}