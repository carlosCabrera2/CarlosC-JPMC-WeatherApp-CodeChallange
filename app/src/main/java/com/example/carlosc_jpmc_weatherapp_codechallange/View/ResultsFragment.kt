package com.example.carlosc_jpmc_weatherapp_codechallange.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.carlosc_jpmc_weatherapp_codechallange.Model.Weather
import com.example.carlosc_jpmc_weatherapp_codechallange.R
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.BaseFragment
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.FailureResponseException
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.ResponseState
import com.example.carlosc_jpmc_weatherapp_codechallange.ViewModel.AppViewModel
import com.example.carlosc_jpmc_weatherapp_codechallange.databinding.FragmentResultsPageBinding


private const val TAG = "ResultsFragment"

class ResultsFragment: BaseFragment() {


    private val binding by lazy {
        FragmentResultsPageBinding.inflate(layoutInflater)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getWeather()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getWeather()
        appViewModel.getWeather()
    }


    private fun getWeather(){
        appViewModel.weather.observe(viewLifecycleOwner){
            state ->
            when(state){
                is ResponseState.LOADING ->{}
                is ResponseState.SUCCESS ->{
                    state.response.let { it ->

                Log.d(TAG, "getWeatherResultsFragment: it.name = ${it.name} ")
                binding.name.text = it.name
                        binding.coordinatesLat.text = it.coord?.lat.toString()
                        binding.coordinatesLong.text = it.coord?.lon.toString()
                        binding.timeZoneData.text = it.timezone.toString()
                        binding.mainFeelsLike.text = (it.main?.feelsLike.toString() + "° fahrenheit")
                        binding.mainHumidity.text = (it.main?.humidity.toString() + "%")
                        binding.mainPressure.text = (it.main?.pressure.toString() + " Milibars")
                        binding.mainTempMaxData.text = (it.main?.tempMax.toString() + "° fahrenheit")
                        binding.mainTempMinData.text = (it.main?.tempMin.toString() + "° fahrenheit")
                        binding.weatherDescription.text = it.weather?.get(0)?.description.toString()
                        binding.cloudsData.text = it.clouds?.all.toString()
                        binding.visibilityData.text = (it.visibility.toString() + "%")
                        binding.windData.text = (it.wind?.speed.toString() + " Nautical MPH")

                        Glide
                            .with(binding.root)
                            .load("https://openweathermap.org/img/wn/"+
                                            it.weather?.get(0)?.icon +
                                            "@2x.png")
                            .centerCrop()
                            .placeholder(R.drawable.baseline_wb_sunny_24)
                            .error(R.drawable.baseline_image_not_supported_24)
                            .into(binding.imageHolder)
                    }
                }
                is ResponseState.ERROR -> {}
            }
        }


    }

}

