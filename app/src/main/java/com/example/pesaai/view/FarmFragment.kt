package com.example.pesaai.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentFarmBinding
import com.example.pesaai.service.model.Farm
import com.example.pesaai.viewmodel.FarmViewModel
import kotlinx.coroutines.launch

class FarmFragment : Fragment() {
    private var _binding: FragmentFarmBinding? = null
    private val binding: FragmentFarmBinding get() = _binding!!

    private val viewModel: FarmViewModel by viewModels()

    private val args: FarmFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        listeners()
        observe()
        loadFarm()
    }

    private fun listeners() = with(binding) {
        btnSave.setOnClickListener { handleSave() }
        btnDelete.setOnClickListener { handleDelete() }

        tilFarm.editText?.addTextChangedListener {
            tvFarm.text = it
        }
        tilLocation.editText?.addTextChangedListener {
            tvLocation.text = it
        }
        tilProprietary.editText?.addTextChangedListener {
            tvProprietary.text = it
        }
    }

    private fun handleDelete() {
        AlertDialog.Builder(context)
            .setTitle("Exclusão de Fazenda")
            .setMessage("Deseja excluir Fazenda?")
            .setPositiveButton("Sim") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    args.farm?.let {
                        viewModel.delete(it)
                        Toast.makeText(
                            context,
                            "Fazenda excluída com sucesso!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.allFarmsFragment)
                    }
                }
            }
            .setNeutralButton("Cancelar", null)
            .show()
    }

    private fun handleSave() = with(binding) {
        val nome = tilFarm.editText?.text.toString()

        if (nome == "") {
            Toast.makeText(context, "Preencha o nome da fazenda", Toast.LENGTH_SHORT).show()
        } else {
            val farm = Farm(
                name = tilFarm.editText?.text.toString(),
                local = tilLocation.editText?.text.toString(),
                proprietor = tilProprietary.editText?.text.toString(),
            )

            viewLifecycleOwner.lifecycleScope.launch {
                if (args.farm?.id == null) {
                    viewModel.insert(farm)
                    Toast.makeText(context, "Fazenda salva com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    args.farm?.also {
                        farm.id = it.id
                    }
                    viewModel.update(farm)
                    Toast.makeText(context, "Fazenda atualizada com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }

                findNavController().navigateUp()

                tilFarm.editText?.text?.clear()
                tilLocation.editText?.text?.clear()
                tilProprietary.editText?.text?.clear()
            }
        }
    }

    private fun observe() {
        viewModel.farm.observe(viewLifecycleOwner) {
            binding.apply {
                tilFarm.editText?.setText(it.name)
                tilLocation.editText?.setText(it.local)
                tilProprietary.editText?.setText(it.proprietor)

                tvFarm.text = tilFarm.editText?.text
                tvLocation.text = tilLocation.editText?.text
                tvProprietary.text = tilProprietary.editText?.text
            }
        }
    }

    private fun loadFarm() {
        viewLifecycleOwner.lifecycleScope.launch {
            args.farm?.let {
                viewModel.load(it.id)
            }
        }

        binding.btnDelete.isVisible = (args.farm?.id != 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}