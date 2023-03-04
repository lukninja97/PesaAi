package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentAllPesagensBinding
import com.example.pesaai.view.adapter.PesagemAdapter
import com.example.pesaai.viewmodel.AllPesagensViewModel

class AllPesagensFragment : Fragment() {
    private var _binding: FragmentAllPesagensBinding? = null
    private val binding: FragmentAllPesagensBinding get() = _binding!!

    private val mViewModel: AllPesagensViewModel by viewModels()
    private lateinit var mAdapter: PesagemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPesagensBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setRecycler()
        listeners()
        observer()
    }

    private fun setRecycler() {
        mAdapter = PesagemAdapter(mViewModel.pesagemList.value) {
            //fazer uma fragment para exibir as pesagens
        }

        val recycler = binding.recyclerAllPesagens
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
    }

    private fun listeners() {
        binding.fabAddPesagem.setOnClickListener {
            findNavController().navigate(R.id.action_pesagens_to_pesar)
        }
    }

    private fun observer() {
        mViewModel.pesagemList.observe(viewLifecycleOwner) {
            mAdapter.updatePesagens(it)

            binding.textVazio.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}