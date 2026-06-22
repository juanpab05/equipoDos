package com.example.pico_botella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pico_botella.databinding.FragmentRulesBinding
import com.example.pico_botella.viewmodel.RulesViewModel

/**
 * Instrucciones del juego.
 *
 * Muestra al jugador cómo se juega "pico botella" y quién gana la partida.
 * El audio de fondo del Home se pausa al entrar (Criterio 1) y se restablece
 * al volver con la flecha atrás si inicialmente estaba en ON (Criterio 3).
 */
class FragmentRules : Fragment() {
    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    // ViewModel propio de esta pantalla (sin datos de negocio por ahora).
    private val rulesViewModel: RulesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
    }

    // La flecha atrás regresa al home. La reanudación del audio
    // (si estaba en ON) se hace en FragmentHome.onResume(), ya que también
    // debe funcionar si el usuario usa el botón físico "atrás" del sistema.
    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}