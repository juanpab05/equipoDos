package com.example.pico_botella.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.pico_botella.databinding.DialogAddChallengeBinding

/**
 * Diálogo mínimo para agregar un challenge.
 * Se incluye para poder probar HU 6.0 de punta a punta (listar + agregar).
 */
class DialogAddChallenge(
    private val onSave: (String) -> Unit
) : DialogFragment() {

    private var _binding: DialogAddChallengeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddChallengeBinding.inflate(layoutInflater)

        binding.btnSave.isEnabled = false
        binding.btnSave.alpha = 0.4f

        binding.etChallenge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val hasText = !s.isNullOrBlank()
                binding.btnSave.isEnabled = hasText
                binding.btnSave.alpha = if (hasText) 1.0f else 0.4f
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
        dialog.setCanceledOnTouchOutside(false)

        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {
            val description = binding.etChallenge.text.toString().trim()
            onSave(description)
            dismiss()
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}