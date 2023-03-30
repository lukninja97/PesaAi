package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.databinding.FragmentAllFarmsBinding
import com.example.pesaai.view.adapter.FarmAdapter
import com.example.pesaai.viewmodel.AllFarmsViewModel

class AllFarmsFragment : Fragment() {
    private var _binding: FragmentAllFarmsBinding? = null
    private val binding: FragmentAllFarmsBinding get() = _binding!!

    private val viewModel: AllFarmsViewModel by viewModels()
    private lateinit var farmAdapter: FarmAdapter

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
        viewModel.load()
    }

    private fun setRecycler() {
        farmAdapter = FarmAdapter(viewModel.farmList.value) { farm ->
            val action = AllFarmsFragmentDirections.farmsToDashboard(farm)
            findNavController().navigate(action)
        }

        with(binding.rvAllFarms) {
            layoutManager = LinearLayoutManager(context)
            adapter = farmAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun listeners() {
        binding.fabAddFarm.setOnClickListener {
            val action = AllFarmsFragmentDirections.farmsToFarmForm(null)
            findNavController().navigate(action)
        }
    }

    private fun observer() {
        viewModel.farmList.observe(viewLifecycleOwner) {
            farmAdapter.updateFarms(it)

            binding.tvEmpty.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}