package com.seufintech.fintechapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seufintech.fintechapp.R
import com.seufintech.fintechapp.data.Transaction
import com.seufintech.fintechapp.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallback) {

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.tvDescription.text = transaction.description

            val formattedValue = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(transaction.value)
            binding.tvValue.text = formattedValue

            if (transaction.type == "Crédito") {
                binding.ivType.setImageResource(R.drawable.ic_credit)
                val greenColor = ContextCompat.getColor(binding.root.context, android.R.color.holo_green_dark)
                binding.tvValue.setTextColor(greenColor)
            } else {
                binding.ivType.setImageResource(R.drawable.ic_debit)
                val redColor = ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark)
                binding.tvValue.setTextColor(redColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }
}
