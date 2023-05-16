package com.example.carlosc_jpmc_weatherapp_codechallange.View

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.carlosc_jpmc_weatherapp_codechallange.R
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.BaseFragment
import com.example.carlosc_jpmc_weatherapp_codechallange.databinding.FragmentSearchPageBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class SearchFragment: BaseFragment(), SearchView.OnQueryTextListener {

    private val binding by lazy {
        FragmentSearchPageBinding.inflate(layoutInflater)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         //   fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
         //   getUserLocation()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val locationButton = binding.btnSearchCurrentLocation

        locationButton.setOnClickListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            getUserLocation()
        }

        //the city form shared preferences and populate the search view
        val savedSearch = requireContext().getSharedPreferences("LOCATION_NAME", Context.MODE_PRIVATE).getString("LOCATION_SEARCH",
            Context.MODE_PRIVATE.toString())

        savedSearch?.let {
            binding.svSearchLocation.setQuery(savedSearch, false)
        }

        binding.svSearchLocation.setOnQueryTextListener(this)

        return binding.root


    }



    private fun getUserLocation() {
        val task = fusedLocationClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            return
        }
        task.addOnSuccessListener {
            it?.let {
              //  appViewModel.getWeather(getCityName(it.latitude, it.longitude))
                Toast.makeText(requireContext(), "Location services enabled", Toast.LENGTH_SHORT).show()

                appViewModel.selectedLocation = getCityName(it.latitude, it.longitude)
                Log.d(TAG, "getUserLocation: selectedLocation = ${appViewModel.selectedLocation}")

            }
        }
    }

    private fun getCityName(latitude: Double, longitude: Double): String? {

        val cityName: String?
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address =geocoder.getFromLocation(latitude, longitude, 3)


        cityName = address?.get(0)?.adminArea
        Log.d(TAG, "getCityName: cityName= $cityName ")
        binding.svSearchLocation.setQuery(cityName, false)

        return cityName
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            }
            else -> {}

        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            Toast.makeText(requireContext(),
                "$query", Toast.LENGTH_LONG).show()
        }

        query?.let {
            // share preferences to save the search for the next launch
            requireContext().getSharedPreferences("LOCATION_NAME", Context.MODE_PRIVATE).edit().apply {
                putString("LOCATION_SEARCH",  it)
            }.apply()



            appViewModel.selectedLocation = it

            findNavController().navigate(R.id.action_search_menu_to_results_menu)

        }

        return true
        }

    override fun onQueryTextChange(newSearch: String?): Boolean = true


}



