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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentFazendaBinding
import com.example.pesaai.service.model.Fazenda
import com.example.pesaai.viewmodel.FazendaViewModel
import kotlinx.coroutines.launch

class FazendaFragment : Fragment() {
    private var _binding: FragmentFazendaBinding? = null
    private val binding: FragmentFazendaBinding get() = _binding!!

    private val viewModel: FazendaViewModel by viewModels()

    private val args: FazendaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFazendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        listeners()
        observe()
        loadFazenda()
    }

    private fun listeners() = with(binding) {
        buttonSave.setOnClickListener { handleSave() }
        buttonDelete.setOnClickListener { handleDelete() }

        editFazenda.editText?.addTextChangedListener {
            textFazenda.text = it
        }
        editLocal.editText?.addTextChangedListener {
            textLocal.text = it
        }
        editProprietario.editText?.addTextChangedListener {
            textDono.text = it
        }
    }

    private fun handleDelete() {
        AlertDialog.Builder(context)
            .setTitle("Exclusão de Fazenda")
            .setMessage("Deseja excluir Fazenda?")
            .setPositiveButton("Sim") { _, _ ->
                lifecycleScope.launch {
                    args.fazenda?.let {
                        viewModel.delete(it)
                        Toast.makeText(
                            context,
                            "Fazenda excluída com sucesso!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.all_fazendas_fragment)
                    }
                }
            }
            .setNeutralButton("Cancelar", null)
            .show()
    }

    private fun handleSave() = with(binding) {
        val nome = editFazenda.editText?.text.toString()

        if (nome == "") {
            Toast.makeText(context, "Preencha o nome da fazenda", Toast.LENGTH_SHORT).show()
        } else {
            val farm = Fazenda(
                nome = editFazenda.editText?.text.toString(),
                local = editLocal.editText?.text.toString(),
                dono = editProprietario.editText?.text.toString(),
            )

            viewLifecycleOwner.lifecycleScope.launch {
                if (args.fazenda?.id == null) {
                    viewModel.insert(farm)
                    Toast.makeText(context, "Fazenda salva com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    args.fazenda?.also {
                        farm.id = it.id
                    }
                    viewModel.update(farm)
                    Toast.makeText(context, "Fazenda atualizada com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }

                findNavController().navigateUp()

                editFazenda.editText?.text?.clear()
                editLocal.editText?.text?.clear()
                editProprietario.editText?.text?.clear()
            }
        }
    }

    private fun observe() {
        viewModel.fazenda.observe(viewLifecycleOwner) {
            binding.apply {
                editFazenda.editText?.setText(it.nome)
                editLocal.editText?.setText(it.local)
                editProprietario.editText?.setText(it.dono)

                textFazenda.text = editFazenda.editText?.text
                textLocal.text = editLocal.editText?.text
                textDono.text = editProprietario.editText?.text
            }
        }
    }

    private fun loadFazenda() {
        viewLifecycleOwner.lifecycleScope.launch {
            args.fazenda?.let {
                viewModel.load(it.id)
            }
        }

        binding.buttonDelete.isVisible = (args.fazenda?.id != 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}