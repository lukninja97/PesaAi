package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentAllPesagensBinding
import com.example.pesaai.view.adapter.PesagemAdapter
import com.example.pesaai.viewmodel.AllPesagensViewModel

class AllPesagensFragment : Fragment() {

    private lateinit var mViewModel: AllPesagensViewModel
    private lateinit var mAdapter: PesagemAdapter

    private var _binding: FragmentAllPesagensBinding? = null
    private val binding: FragmentAllPesagensBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(AllPesagensViewModel::class.java)

        _binding = FragmentAllPesagensBinding.inflate(inflater, container, false)

        binding.fabAddPesagem.setOnClickListener {
            findNavController().navigate(R.id.action_pesagens_to_pesar)
        }

        mAdapter = PesagemAdapter(mViewModel.pesagemList.value){
            //fazer uma fragment para exibir as pesagens
        }

        val recycler = binding.recyclerAllPesagens
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        lifecycle.addObserver(mViewModel)

        observer()

        return binding.root
    }

    private fun observer(){
        mViewModel.pesagemList.observe(viewLifecycleOwner, {
            mAdapter.updatePesagens(it)

            binding.textVazio.isVisible = it.isEmpty()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}