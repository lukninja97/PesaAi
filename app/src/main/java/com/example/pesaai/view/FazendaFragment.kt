package com.example.pesaai.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentFazendaBinding
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Farm
import com.example.pesaai.viewmodel.FazendaViewModel
import kotlinx.coroutines.launch

class FazendaFragment : Fragment() {

    private lateinit var mViewModel: FazendaViewModel

    private val args: FazendaFragmentArgs by navArgs()

    private var _binding: FragmentFazendaBinding? = null
    private val binding: FragmentFazendaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(FazendaViewModel::class.java)

        _binding = FragmentFazendaBinding.inflate(inflater, container, false)

        //clicksListener()

        binding.apply {
            buttonSave.setOnClickListener { handleSave() }
            buttonDelete.setOnClickListener { handleDelete() }

            editFazenda.editText?.addTextChangedListener {
                tvFarm.text = it
            }
            editLocal.editText?.addTextChangedListener {
                tvLocal.text = it
            }
            editProprietario.editText?.addTextChangedListener {
                tvEmployee.text = it
            }
        }

        observe()

        loadFazenda()

        return binding.root
    }

    private fun clicksListener() {
        binding.apply {
            buttonSave.setOnClickListener {
                handleSave()
            }

            buttonDelete.setOnClickListener {
                handleDelete()
            }
        }
    }

    private fun handleDelete() {
        AlertDialog.Builder(context)
            .setTitle("Exclusão de Fazenda")
            .setMessage("Deseja excluir Fazenda?")
            .setPositiveButton("Sim") { dialog, which ->
                lifecycleScope.launch {
                    args.fazenda?.let {
                        mViewModel.delete(it)
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

    private fun handleSave() {
        val nome: String = binding.editFazenda.editText?.text.toString()

        if (nome == "") {
            Toast.makeText(context, "O Nome não pode ser nulo", Toast.LENGTH_SHORT).show()
        } else {
            var farm = Farm(
                nome = binding.editFazenda.editText?.text.toString(),
                local = binding.editLocal.editText?.text.toString(),
                dono = binding.editProprietario.editText?.text.toString(),
            )

            lifecycleScope.launch {

                /*args.fazenda?.id?.let {
                    mViewModel.update(fazenda)
                    Toast.makeText(context, "Fazenda atualizado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                } ?: run {
                    mViewModel.insert(fazenda)
                    Toast.makeText(context, "Fazenda salva com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }*/

                if (args.fazenda?.id == null) {
                    mViewModel.insert(farm)
                    Toast.makeText(context, "Fazenda salvo com sucesso", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    args.fazenda?.also {
                        farm.id = it.id
                    }
                    mViewModel.update(farm)
                    Toast.makeText(
                        context,
                        "Fazenda atualizado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Navigation.findNavController(binding.root)
                    .navigate(FazendaFragmentDirections.actionFazendaFormToFazendas(""))
            }

            binding.apply {
                editFazenda.editText?.text?.clear()
                editLocal.editText?.text?.clear()
                editProprietario.editText?.text?.clear()
            }
        }
    }

    private fun observe() {
        mViewModel.farm.observe(viewLifecycleOwner) {
            binding.apply {
                editFazenda.editText?.setText(it.nome)
                editLocal.editText?.setText(it.local)
                editProprietario.editText?.setText(it.dono)

                tvFarm.text = editFazenda.editText?.text
                tvLocal.text = editLocal.editText?.text
                tvEmployee.text = editProprietario.editText?.text
            }
        }
    }

    private fun loadFazenda() {
        lifecycleScope.launch {
            args.fazenda?.let {
                mViewModel.load(it.id)
            }
        }

        binding.buttonDelete.isVisible = (args.fazenda?.id != 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}