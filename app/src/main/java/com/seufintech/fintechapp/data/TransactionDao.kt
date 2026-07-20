package com.seufintech.fintechapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Interface que define como vamos interagir com o banco de dados.
@Dao
interface TransactionDao {

    // Insere uma nova transação. 'suspend' indica que deve ser chamada de uma coroutine.
    @Insert
    suspend fun insert(transaction: Transaction)

    // Pega todas as transações, ordenadas da mais nova para a mais antiga.
    // 'Flow' faz com que a lista se atualize automaticamente na tela quando algo muda.
    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAll(): Flow<List<Transaction>>

    // Calcula o saldo total.
    // 'Flow' também atualiza o saldo automaticamente.
    @Query("SELECT SUM(CASE WHEN type = 'Crédito' THEN value ELSE -value END) FROM transactions")
    fun getBalance(): Flow<Double?>
}
