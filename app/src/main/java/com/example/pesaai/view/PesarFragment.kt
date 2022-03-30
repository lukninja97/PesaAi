package com.example.pesaai.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var mViewModel: PesagemViewModel
    private lateinit var mBoiViewModel: BoiViewModel
    private lateinit var mAdapter: BoiAdapter

    private val args: PesarFragmentArgs by navArgs()

    private var _binding: FragmentPesarBinding? = null
    private val binding: FragmentPesarBinding get() = _binding!!

    private val mBoiList = MutableLiveData<List<Boi>>()
    val boiList: LiveData<List<Boi>> = mBoiList

    private var bois: ArrayList<Boi> = arrayListOf()
    private var pesototal: Float = 0F
    private var arrobatotal: Float = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(PesagemViewModel::class.java)
        mBoiViewModel = ViewModelProvider(this).get(BoiViewModel::class.java)

        _binding = FragmentPesarBinding.inflate(inflater, container, false)

        binding.apply {
            buttonPesar.setOnClickListener { handlePesar() }
            buttonFinalizar.setOnClickListener { handleFinalizar() }

            textFazenda.setText(args.fazenda?.nome)
            textLocal.setText(args.fazenda?.local)
            textDono.setText(args.fazenda?.dono)
            editDataPesagem.editText?.setText(getDataAtual())
        }

        mAdapter = BoiAdapter(mViewModel.boiList.value){}

        val recycler = binding.listPesados
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mViewModel.loadBois(id)

        observe()

        return binding.root
    }

    private fun handlePesar(){
        binding.apply {
            if (editBrinco.isNotEmpty() && editPeso.isNotEmpty()){
                val boi = Boi(
                    brinco = editBrinco.editText?.text.toString(),
                    peso = editPeso.editText?.text.toString().toFloat(),
                    arroba = editPeso.editText?.text.toString().toFloat() / 15
                )
                bois.add(boi)

                pesototal = 0F
                arrobatotal = 0F

                bois.forEach {
                    pesototal += it.peso
                    arrobatotal += it.arroba

                    println("pesototal${pesototal} arrobatotal${arrobatotal}")
                }

                textQuantidade.setText(bois.count().toString())
                textPesoMedio.setText((pesototal / bois.count()).toString())
                textArrobaMedia.setText((arrobatotal / bois.count()).toString())


                println("boi${bois}")

                mBoiViewModel.insert(boi)

                editBrinco.editText?.text?.clear()
                editBrinco.editText?.focusable
                editPeso.editText?.text?.clear()

            }else{
                Toast.makeText(context, "Preencha o brinco e o peso", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun handleFinalizar(){
        binding.apply {
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
                    .setPositiveButton("Sim") { dialog, which ->
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
                textQuantidade.setText("Quantidade")
                textPesoMedio.setText("Peso Médio")
                textArrobaMedia.setText("Arroba Média")
                bois.removeAll(bois)
            } ?: kotlin.run {

            }
        }
    }

    private fun observe(){
        mViewModel.boiList.observe(viewLifecycleOwner, {
            mAdapter.updateBois(bois)
        })
    }

    private fun getDataAtual(): String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}