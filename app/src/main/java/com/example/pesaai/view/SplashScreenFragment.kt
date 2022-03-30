package com.example.pesaai.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pesaai.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setFullScreen()
        _binding = FragmentSplashBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        finishSplashScreen()
    }

    private fun setFullScreen() {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setDefaultScreen() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun finishSplashScreen() {
        lifecycleScope.launch {
            delay(8000L)
            //findNavController().navigate(R.id.action_splashScreenFragment_to_FirstFragment)
            delay(1200L)
            setDefaultScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}