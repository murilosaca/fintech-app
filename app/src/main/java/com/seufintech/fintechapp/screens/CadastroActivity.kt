package com.seufintech.fintechapp.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.seufintech.fintechapp.R
import com.seufintech.fintechapp.data.AppDatabase
import com.seufintech.fintechapp.data.Transaction
import com.seufintech.fintechapp.databinding.ActivityCadastroBinding
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    // Acessa o banco de dados de forma 'lazy' (só é criado quando usado pela primeira vez)
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val transactionDao by lazy { database.transactionDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Adiciona o botão de "voltar" na barra de título
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSalvar.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val description = binding.etDescricao.text.toString().trim()
        val valueStr = binding.etValor.text.toString().replace(',', '.')

        if (description.isBlank() || valueStr.isBlank()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val value = valueStr.toDoubleOrNull()
        if (value == null || value <= 0) {
            Toast.makeText(this, "O valor inserido é inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        val type = if (binding.rbCredito.isChecked) "Crédito" else "Débito"

        val transaction = Transaction(type = type, description = description, value = value)

        // Salva a transação no banco de dados usando uma Coroutine para não travar a tela
        lifecycleScope.launch {
            transactionDao.insert(transaction)
            Toast.makeText(this@CadastroActivity, "Transação salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Fecha a tela de cadastro e volta para a anterior
        }
    }

    // Função para o botão de "voltar" da barra de título funcionar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
