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
import com.example.pesaai.databinding.FragmentAllWeightsBinding
import com.example.pesaai.view.adapter.WeighingAdapter
import com.example.pesaai.viewmodel.AllWeightsViewModel

class AllWeightsFragment : Fragment() {
    private var _binding: FragmentAllWeightsBinding? = null
    private val binding: FragmentAllWeightsBinding get() = _binding!!

    private val viewModel: AllWeightsViewModel by viewModels()
    private lateinit var adapter: WeighingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllWeightsBinding.inflate(inflater, container, false)
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
        adapter = WeighingAdapter(viewModel.weightList.value) {
            //fazer uma fragment para exibir as pesagens
        }

        val recycler = binding.recyclerAllPesagens
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
    }

    private fun listeners() {
        binding.fabAddPesagem.setOnClickListener {
            findNavController().navigate(R.id.action_pesagens_to_pesar)
        }
    }

    private fun observer() {
        viewModel.weightList.observe(viewLifecycleOwner) {
            adapter.updateWeights(it)

            binding.textVazio.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}