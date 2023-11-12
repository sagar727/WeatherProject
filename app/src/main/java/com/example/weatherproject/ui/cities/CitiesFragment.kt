package com.example.weatherproject.ui.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherproject.R
import com.example.weatherproject.databinding.FragmentCitiesBinding
import com.example.weatherproject.network.RetrofitProvider

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    var cityArray: ArrayList<Cities>? = null
    lateinit var viewModel: CitiesViewModel
    lateinit var adapter: CitiesAdapter

    var place = ArrayList<String>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[CitiesViewModel::class.java]

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val key = getString(R.string.apiKey)

        binding.recycler.layoutManager = LinearLayoutManager(context)

        // Initialize cityArray here
        cityArray = ArrayList()

        place = viewModel.getCities(requireContext())

        for (city in place) {
            viewModel.getCityData(requireContext(), key, city)
        }
        listenLiveData()
        cityArray?.clear()
        return root
    }
    private fun listenLiveData() {
        viewModel.cityList.observe(viewLifecycleOwner) { cities ->
            cityArray = cities
            adapter = CitiesAdapter(requireContext(), cityArray)
            binding.recycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}