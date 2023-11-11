package com.example.weatherproject.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherproject.databinding.FragmentCitiesBinding

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val citiesViewModel =
            ViewModelProvider(this).get(CitiesViewModel::class.java)

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cityText = binding.textCities
        citiesViewModel.text.observe(viewLifecycleOwner) {
            cityText.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}