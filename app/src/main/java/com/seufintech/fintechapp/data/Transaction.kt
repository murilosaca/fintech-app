package com.seufintech.fintechapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa a tabela "transactions" no banco de dados.
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // "Crédito" ou "Débito"
    val description: String,
    val value: Double
)
