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
import com.example.pesaai.databinding.FragmentAllFazendasBinding
import com.example.pesaai.view.adapter.FazendaAdapter
import com.example.pesaai.viewmodel.AllFazendasViewModel

class AllFazendasFragment : Fragment() {
    private var _binding: FragmentAllFazendasBinding? = null
    private val binding: FragmentAllFazendasBinding get() = _binding!!

    private val mViewModel: AllFazendasViewModel by viewModels()
    private lateinit var mAdapter: FazendaAdapter

    private val args: AllFazendasFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFazendasBinding.inflate(inflater, container, false)
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
        mAdapter = FazendaAdapter(mViewModel.fazendaList.value) { farm ->
            if (args.origem == "F") {
                val action = AllFazendasFragmentDirections.actionFazendasToFazendaForm(farm)
                findNavController().navigate(action)
            } else {
                val action = AllFazendasFragmentDirections.actionFazendasToPesar(farm)
                findNavController().navigate(action)
            }
        }

        val recycler = binding.recyclerAllFazendas
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
    }

    private fun listeners() {
        binding.fabAddFazenda.setOnClickListener {
            val action = AllFazendasFragmentDirections.actionFazendasToFazendaForm(null)
            findNavController().navigate(action)
        }
    }

    private fun observer() {
        mViewModel.fazendaList.observe(viewLifecycleOwner) {
            mAdapter.updateFazendas(it)

            binding.textVazio.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}