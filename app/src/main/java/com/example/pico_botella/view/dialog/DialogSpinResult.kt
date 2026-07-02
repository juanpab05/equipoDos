package com.example.pico_botella.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import com.example.pico_botella.databinding.DialogSpinResultBinding
import com.example.pico_botella.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.drawable.toDrawable

class DialogSpinResult(
    private val challengeDescription: String,
    private val onDismiss: () -> Unit = {}
) : DialogFragment() {

    private var _binding: DialogSpinResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSpinResultBinding.inflate(layoutInflater)

        binding.tvChallenge.text = challengeDescription

        loadRandomPokemon()

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    private fun loadRandomPokemon() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val pokemon = PokemonRepository().getRandomPokemon()
                if (pokemon != null) {
                    binding.ivPokemon.load(pokemon.img)
                }
            } catch (_: Exception) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
