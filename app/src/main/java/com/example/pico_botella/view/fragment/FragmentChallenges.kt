package com.example.pico_botella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pico_botella.databinding.FragmentChallengesBinding
import com.example.pico_botella.model.Challenge
import com.example.pico_botella.view.adapter.ChallengeAdapter
import com.example.pico_botella.view.dialog.DialogAddChallenge
import com.example.pico_botella.viewmodel.ChallengesViewModel
/**
 * Agregar y listar retos.
 * se lanzan desde esta pantalla.
 */
class FragmentChallenges : Fragment() {
    private var _binding: FragmentChallengesBinding? = null
    private val binding get() = _binding!!

    private val challengesViewModel: ChallengesViewModel by activityViewModels()

    private lateinit var adapter: ChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = ChallengeAdapter(
            onEditClick = { challenge -> showEditDialog(challenge) },
            onDeleteClick = { challenge -> showDeleteDialog(challenge) }
        )
        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter
    }

    private fun setupObservers() {
        // Cada vez que cambia la lista en Room, el RecyclerView se actualiza automáticamente
        challengesViewModel.allChallenges.observe(viewLifecycleOwner) { challenges ->
            adapter.submitList(challenges)
        }
    }

    private fun setupListeners() {
        // Botón atrás de la toolbar
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // FAB naranja para agregar reto
        binding.fabAddChallenge.setOnClickListener {
            showAddDialog()
        }
    }

    // Cuadro de diálogo agregar reto
    private fun showAddDialog() {
        DialogAddChallenge { description ->
            challengesViewModel.insert(Challenge(description = description))
        }.show(parentFragmentManager, "DialogAgregarReto")
    }

    //  (HU 8.0) Cuadro de diálogo editar reto
    private fun showEditDialog(challenge: Challenge) {
    }

    // ( (HU 9.0) Cuadro de diálogo eliminar reto
    private fun showDeleteDialog(challenge: Challenge) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}