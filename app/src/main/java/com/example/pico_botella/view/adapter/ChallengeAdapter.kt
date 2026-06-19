package com.example.pico_botella.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pico_botella.databinding.ItemChallengeBinding
import com.example.pico_botella.model.Challenge


/**
 * Adapter para el RecyclerView de retos.
 * Usa ListAdapter + DiffUtil para actualizaciones eficientes de la lista.
 */
class ChallengeAdapter (
    private val onEditClick: (Challenge) -> Unit,
    private val onDeleteClick: (Challenge) -> Unit
): ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Challenge>() {
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge) = oldItem == newItem
    }

    inner class ChallengeViewHolder(private val binding: ItemChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: Challenge) {
            binding.tvChallengeDescription.text = challenge.description
            binding.ivEditChallenge.setOnClickListener { onEditClick(challenge) }
            binding.ivDeleteChallenge.setOnClickListener { onDeleteClick(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}