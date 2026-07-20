package com.seufintech.fintechapp.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seufintech.fintechapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.btnExtrato.setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }

        binding.btnSair.setOnClickListener {
            finishAffinity() // Fecha o aplicativo e todas as suas activities
        }
    }
}
