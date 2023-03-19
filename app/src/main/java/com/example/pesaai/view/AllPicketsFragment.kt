package com.example.pesaai.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.databinding.FragmentAllPicketsBinding
import com.example.pesaai.view.adapter.PicketAdapter
import com.example.pesaai.viewmodel.AllPicketsViewModel

class AllPicketsFragment : Fragment() {
    private var _binding: FragmentAllPicketsBinding? = null
    private val binding: FragmentAllPicketsBinding get() = _binding!!

    private val viewModel: AllPicketsViewModel by viewModels()
    private lateinit var picketAdapter: PicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPicketsBinding.inflate(inflater, container, false)
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
        picketAdapter = PicketAdapter(viewModel.picketList.value) {
            //TODO: fazer fragment do picket
        }

        with(binding.rvAllPickets) {
            layoutManager = LinearLayoutManager(context)
            adapter = picketAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun listeners() {
        binding.fabAddPicket.setOnClickListener {
            val action = AllFarmsFragmentDirections.farmsToFarmForm(null)
            findNavController().navigate(action)
        }
    }

    private fun observer() {
        viewModel.picketList.observe(viewLifecycleOwner) {
            picketAdapter.updatePickets(it)

            binding.tvEmpty.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}