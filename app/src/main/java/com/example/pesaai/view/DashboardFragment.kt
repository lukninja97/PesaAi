package com.example.pesaai.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.R
import com.example.pesaai.viewmodel.DashboardViewModel
import com.example.pesaai.databinding.FragmentDashboardBinding
import com.example.pesaai.view.adapter.PicketAdapter

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var picketAdapter: PicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setAdapter()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        cardFarm.setOnClickListener {
            findNavController().navigate(R.id.allFarmsFragment)
        }

        cardAnimals.setOnClickListener {
            // TODO Navegar para a tela de animais presentes na fazenda
        }

        cardPickets.setOnClickListener {
            findNavController().navigate(R.id.allPicketsFragment)
        }
    }
    private fun setAdapter() {
        picketAdapter = PicketAdapter(viewModel.picketsList.value) {

        }

        binding.rvHistoric.apply {
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
}