package com.example.pesaai.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentPesarBinding
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.Pesagem
import com.example.pesaai.view.adapter.BoiAdapter
import com.example.pesaai.viewmodel.BoiViewModel
import com.example.pesaai.viewmodel.PesagemViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PesarFragment : Fragment() {

    private val mViewModel: PesagemViewModel by viewModels()
    private val mBoiViewModel: BoiViewModel by viewModels()
    private lateinit var mAdapter: BoiAdapter

    private val args: PesarFragmentArgs by navArgs()

    private var _binding: FragmentPesarBinding? = null
    private val binding: FragmentPesarBinding get() = _binding!!

    private var bois: ArrayList<Boi> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPesarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScreen()
        setupAdapter()
        mViewModel.loadBois(id)
        observe()
    }

    private fun setupScreen() = with(binding) {
        listeners()

        args.fazenda?.also { farm ->
            textFazenda.text = farm.nome
            textLocal.text = farm.local
            textDono.text = farm.dono
        }
        editDataPesagem.editText?.setText(getDataAtual())
    }

    private fun listeners() = with(binding) {
        buttonPesar.setOnClickListener { handleBalance() }
        buttonFinalizar.setOnClickListener { handleFinishBalance() }
    }

    private fun setupAdapter() {
        mAdapter = BoiAdapter(mViewModel.boiList.value) {}

        val recycler = binding.listPesados
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
    }

    private fun handleBalance() {
        binding.apply {
            if (editBrinco.isNotEmpty() && editPeso.isNotEmpty()) {
                val boi = Boi(
                    brinco = editBrinco.editText?.text.toString(),
                    peso = editPeso.editText?.text.toString().toFloat(),
                    arroba = editPeso.editText?.text.toString().toFloat() / 15
                )
                bois.add(boi)

                var pesototal = 0F
                var arrobatotal = 0F

                bois.forEach {
                    pesototal += it.peso
                    arrobatotal += it.arroba

                    println("pesototal${pesototal} arrobatotal${arrobatotal}")
                }

                textQuantidade.text = bois.count().toString()
                textPesoMedio.text = (pesototal / bois.count()).toString()
                textArrobaMedia.text = (arrobatotal / bois.count()).toString()

                println("boi${bois}")

                mBoiViewModel.insert(boi)

                editBrinco.editText?.text?.clear()
                editBrinco.editText?.focusable
                editPeso.editText?.text?.clear()
            } else {
                Toast.makeText(context, "Preencha o brinco e o peso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleFinishBalance() = with(binding) {
        args.fazenda?.also {
            val pesagem = Pesagem(
                fazenda = it.id,
                data = editDataPesagem.editText?.text.toString(),
                funcionario = editFuncionario.editText?.text.toString(),
                finalidade = editFinalidade.editText?.text.toString(),
                qntTotal = textQuantidade.text.toString().toInt(),
                pesoMedio = textPesoMedio.text.toString().toFloat(),
                arrobaMedia = textArrobaMedia.text.toString().toFloat(),
                listBois = arrayListOf(bois.toString())
            )

            AlertDialog.Builder(context)
                .setTitle("Acabou a pesagem?")
                .setMessage("Deseja finalizar a pesagem?")
                .setPositiveButton("Sim") { _, _ ->
                    lifecycleScope.launch {
                        args.fazenda?.let {
                            mViewModel.insert(pesagem)
                            Toast.makeText(
                                context,
                                "Pesagem finalizada com sucesso!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.all_pesagens_fragment)
                        }
                    }
                }
                .setNeutralButton("Cancelar", null)
                .show()

            editFuncionario.editText?.text?.clear()
            editFinalidade.editText?.text?.clear()
            textQuantidade.text = "Quantidade"
            textPesoMedio.text = "Peso Médio"
            textArrobaMedia.text = "Arroba Média"
            bois.removeAll(bois)
        } ?: run {

        }
    }

    private fun observe() {
        mViewModel.boiList.observe(viewLifecycleOwner) {
            mAdapter.updateBois(bois)
        }
    }

    private fun getDataAtual(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}