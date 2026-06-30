package com.example.pico_botella.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.pico_botella.databinding.DialogEditChallengeBinding
import com.example.pico_botella.model.Challenge

/**
 * Diálogo de edición de un reto (HU 8.0).
 *
 * Recibe el [challenge] actual y un callback [onSave] con el objeto ya
 * actualizado que el Fragment enviará al ViewModel para persistirlo en BD.
 */
class DialogEditChallenge(
    private val challenge: Challenge,
    private val onSave: (Challenge) -> Unit
) : DialogFragment() {

    private var _binding: DialogEditChallengeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditChallengeBinding.inflate(layoutInflater)

        // Criterio 3: pre-cargar la descripción actual del reto en la caja de texto
        binding.etChallenge.setText(challenge.description)

        // Botón Guardar deshabilitado si la caja queda vacía
        updateSaveButtonState(challenge.description)

        binding.etChallenge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSaveButtonState(s?.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        // Criterio 7: el diálogo solo se cierra con Cancelar o Guardar
        dialog.setCanceledOnTouchOutside(false)

        // Criterio 4: Cancelar → solo cierra el diálogo
        binding.btnCancel.setOnClickListener { dismiss() }

        // Criterio 5 y 6: Guardar → actualiza en BD y cierra el diálogo
        binding.btnSave.setOnClickListener {
            val newDescription = binding.etChallenge.text.toString().trim()
            // Se conserva el mismo id para que Room haga UPDATE y no INSERT
            onSave(challenge.copy(description = newDescription))
            dismiss()
        }

        return dialog
    }

    private fun updateSaveButtonState(text: String?) {
        val hasText = !text.isNullOrBlank()
        binding.btnSave.isEnabled = hasText
        binding.btnSave.alpha = if (hasText) 1.0f else 0.4f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
