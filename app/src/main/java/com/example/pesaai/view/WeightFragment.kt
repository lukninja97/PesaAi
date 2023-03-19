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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentPesarBinding
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.model.Weight
import com.example.pesaai.view.adapter.BullAdapter
import com.example.pesaai.viewmodel.BullViewModel
import com.example.pesaai.viewmodel.WeightViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeightFragment : Fragment() {
    private var _binding: FragmentPesarBinding? = null
    private val binding: FragmentPesarBinding get() = _binding!!

    private val mViewModel: WeightViewModel by viewModels()
    private val mBullViewModel: BullViewModel by viewModels()
    private lateinit var mAdapter: BullAdapter

    private val args: WeightFragmentArgs by navArgs()
    private var bulls: ArrayList<Bull> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPesarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setupScreen()
        setupAdapter()
        mViewModel.loadBulls(id)
        observe()
    }

    private fun setupScreen() = with(binding) {
        listeners()

        args.farm?.also { farm ->
//            textFazenda.text = farm.name
//            textLocal.text = farm.local
//            textDono.text = farm.proprietor
        }
        editDataPesagem.editText?.setText(getActualDate())
    }

    private fun listeners() = with(binding) {
        buttonPesar.setOnClickListener { handleBalance() }
        buttonFinalizar.setOnClickListener { handleFinishBalance() }
    }

    private fun setupAdapter() {
        mAdapter = BullAdapter(mViewModel.bullList.value) {}

        with(binding.listPesados) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun handleBalance() {
        binding.apply {
            if (editBrinco.isNotEmpty() && editPeso.isNotEmpty()) {
                val bull = Bull(
                    brinco = editBrinco.editText?.text.toString(),
                    weight = editPeso.editText?.text.toString().toFloat(),
                    arroba = editPeso.editText?.text.toString().toFloat() / 15
                )
                bulls.add(bull)

                var pesototal = 0F
                var arrobatotal = 0F

                bulls.forEach {
                    pesototal += it.weight
                    arrobatotal += it.arroba

                    println("pesototal${pesototal} arrobatotal${arrobatotal}")
                }

                textQuantidade.text = bulls.count().toString()
                textPesoMedio.text = (pesototal / bulls.count()).toString()
                textArrobaMedia.text = (arrobatotal / bulls.count()).toString()

                println("boi${bulls}")

                mBullViewModel.insert(bull)

                editBrinco.editText?.text?.clear()
                editBrinco.editText?.focusable
                editPeso.editText?.text?.clear()
            } else {
                Toast.makeText(context, "Preencha o brinco e o peso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleFinishBalance() = with(binding) {
        args.farm?.also {
            val weight = Weight(
                farm = it.id,
                date = editDataPesagem.editText?.text.toString(),
                employee = editFuncionario.editText?.text.toString(),
                finality = editFinalidade.editText?.text.toString(),
                qntTotal = textQuantidade.text.toString().toInt(),
                middleWeight = textPesoMedio.text.toString().toFloat(),
                arrobaMedia = textArrobaMedia.text.toString().toFloat(),
                listBulls = arrayListOf(bulls.toString())
            )

            AlertDialog.Builder(context)
                .setTitle("Acabou a pesagem?")
                .setMessage("Deseja finalizar a pesagem?")
                .setPositiveButton("Sim") { _, _ ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        args.farm?.let {
                            mViewModel.insert(weight)
                            Toast.makeText(
                                context,
                                "Pesagem finalizada com sucesso!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.allWeightsFragment)
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
            bulls.removeAll(bulls)
        } ?: run {

        }
    }

    private fun observe() {
        mViewModel.bullList.observe(viewLifecycleOwner) {
            mAdapter.updateBulls(bulls)
        }
    }

    private fun getActualDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}