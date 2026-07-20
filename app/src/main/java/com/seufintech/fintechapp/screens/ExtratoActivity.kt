package com.seufintech.fintechapp.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.seufintech.fintechapp.R
import com.seufintech.fintechapp.adapter.TransactionAdapter
import com.seufintech.fintechapp.data.AppDatabase
import com.seufintech.fintechapp.data.Transaction
import com.seufintech.fintechapp.databinding.ActivityExtratoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ExtratoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtratoBinding
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val transactionDao by lazy { database.transactionDao() }
    private lateinit var adapter: TransactionAdapter

    // Armazena a lista completa de transações para poder filtrar sem ir ao banco toda hora
    private var fullTransactionList: List<Transaction> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtratoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = TransactionAdapter()
        binding.rvTransactions.adapter = adapter

        observeBalance()
        observeTransactions()
        setupFilterChips()
    }

    // Observa o saldo total e atualiza o TextView na tela
    private fun observeBalance() {
        lifecycleScope.launch {
            transactionDao.getBalance().collectLatest { balance ->
                val validBalance = balance ?: 0.0 // Se o saldo for nulo (sem transações), usa 0.0
                val formattedBalance = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(validBalance)
                binding.tvBalanceValue.text = formattedBalance
            }
        }
    }

    // Observa todas as transações do banco de dados
    private fun observeTransactions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionDao.getAll().collectLatest { transactions ->
                    fullTransactionList = transactions // Guarda a lista completa
                    filterAndSubmitList() // Filtra e exibe a lista
                }
            }
        }
    }

    // Configura os botões de filtro (Chips)
    private fun setupFilterChips() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
            // Quando um chip é clicado, apenas filtra a lista que já temos em memória
            filterAndSubmitList()
        }
    }

    // Filtra a lista 'fullTransactionList' com base no chip selecionado e a envia para o adapter
    private fun filterAndSubmitList() {
        val filteredList = when (binding.chipGroupFilter.checkedChipId) {
            R.id.chip_creditos -> fullTransactionList.filter { it.type == "Crédito" }
            R.id.chip_debitos -> fullTransactionList.filter { it.type == "Débito" }
            else -> fullTransactionList // Caso "Todas"
        }
        adapter.submitList(filteredList)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
