package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesaai.databinding.FragmentAllFazendasBinding
import com.example.pesaai.view.adapter.FazendaAdapter
import com.example.pesaai.viewmodel.AllFazendasViewModel

class AllFazendasFragment : Fragment() {

    private lateinit var mViewModel: AllFazendasViewModel
    private lateinit var mAdapter: FazendaAdapter

    private val args: AllFazendasFragmentArgs by navArgs()

    private var _binding: FragmentAllFazendasBinding? = null
    private val binding: FragmentAllFazendasBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(AllFazendasViewModel::class.java)

        _binding = FragmentAllFazendasBinding.inflate(inflater, container, false)

        binding.fabAddFazenda.setOnClickListener {
            val action = AllFazendasFragmentDirections.actionFazendasToFazendaForm(null)

            Navigation.findNavController(binding.root).navigate(action)
        }

        mAdapter = FazendaAdapter(mViewModel.farmList.value){ fazenda ->
            if (args.origem == "F"){
                val action = AllFazendasFragmentDirections.actionFazendasToFazendaForm(fazenda)
                Navigation.findNavController(binding.root).navigate(action)
            }else{
                val action = AllFazendasFragmentDirections.actionFazendasToPesar(fazenda)
                Navigation.findNavController(binding.root).navigate(action)
            }

        }

        val recycler = binding.recyclerAllFazendas
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        lifecycle.addObserver(mViewModel)

        observer()

        return binding.root
    }

    private fun observer(){
        mViewModel.farmList.observe(viewLifecycleOwner, {
            mAdapter.updateFazendas(it)

            binding.textVazio.isVisible = it.isEmpty()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}