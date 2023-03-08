package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.databinding.FragmentAllFarmsBinding
import com.example.pesaai.view.adapter.FarmAdapter
import com.example.pesaai.viewmodel.AllFarmsViewModel

class AllFarmsFragment : Fragment() {
    private var _binding: FragmentAllFarmsBinding? = null
    private val binding: FragmentAllFarmsBinding get() = _binding!!

    private val viewModel: AllFarmsViewModel by viewModels()
    private lateinit var adapter: FarmAdapter

    private val args: AllFarmsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFarmsBinding.inflate(inflater, container, false)
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
        adapter = FarmAdapter(viewModel.farmList.value) { farm ->
            if (args.origin == "F") {
                val action = AllFarmsFragmentDirections.actionFazendasToFazendaForm(farm)
                findNavController().navigate(action)
            } else {
                val action = AllFarmsFragmentDirections.actionFazendasToPesar(farm)
                findNavController().navigate(action)
            }
        }

        val recycler = binding.recyclerAllFazendas
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
    }

    private fun listeners() {
        binding.fabAddFazenda.setOnClickListener {
            val action = AllFarmsFragmentDirections.actionFazendasToFazendaForm(null)
            findNavController().navigate(action)
        }
    }

    private fun observer() {
        viewModel.farmList.observe(viewLifecycleOwner) {
            adapter.updateFarms(it)

            binding.textVazio.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}