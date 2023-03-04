package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pesaai.R
import com.example.pesaai.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners() = with(binding) {
        fazendas.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuToFazendas("F")
            findNavController().navigate(action)
        }
        pesar.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuToPesar("P")
            findNavController().navigate(action)
        }
        pesagens.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_pesagens)
        }
        admob.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}