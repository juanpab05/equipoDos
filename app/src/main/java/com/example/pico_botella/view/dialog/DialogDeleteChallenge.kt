package com.example.pico_botella.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.pico_botella.databinding.DialogDeleteChallengeBinding
import com.example.pico_botella.model.Challenge

/**
 * Diálogo de confirmación para eliminar un reto (HU 9.0).
 *
 * Recibe el [challenge] que se desea eliminar y un callback [onConfirm]
 * que el Fragment invoca sobre el ViewModel cuando el jugador pulsa "SI".
 */
class DialogDeleteChallenge(
    private val challenge: Challenge,
    private val onConfirm: (Challenge) -> Unit
) : DialogFragment() {

    private var _binding: DialogDeleteChallengeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDeleteChallengeBinding.inflate(layoutInflater)

        // Criterio 3: mostrar la descripción del reto que viene de la BD
        binding.tvChallengeDescription.text = challenge.description

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        // Criterio 6: el diálogo solo se cierra con NO o SI
        dialog.setCanceledOnTouchOutside(false)

        // Criterio 4: NO → solo cierra el diálogo
        binding.btnNo.setOnClickListener { dismiss() }

        // Criterio 5: SI → elimina el reto y cierra el diálogo
        binding.btnYes.setOnClickListener {
            onConfirm(challenge)
            dismiss()
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
