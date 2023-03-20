package br.com.atividade1n1.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pizza (
    val nome: String,
    val valor: Double,
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0
    ) {
    override fun toString(): String {
        return "$Id | $nome | $valor"
    }
}