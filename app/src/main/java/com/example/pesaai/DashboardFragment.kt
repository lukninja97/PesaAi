package com.example.pesaai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.databinding.FragmentDashboardBinding
import com.example.pesaai.view.adapter.PesagemAdapter

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var picketAdapter: PesagemAdapter

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
    }

    private fun setAdapter() {
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