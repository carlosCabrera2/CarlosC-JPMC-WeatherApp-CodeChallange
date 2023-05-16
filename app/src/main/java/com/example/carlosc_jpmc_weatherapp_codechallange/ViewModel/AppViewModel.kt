package com.example.carlosc_jpmc_weatherapp_codechallange.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlosc_jpmc_weatherapp_codechallange.Model.WeatherResponseItem
import com.example.carlosc_jpmc_weatherapp_codechallange.REST.AppRepository
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val ioDispatcher: CoroutineDispatcher,
    sharedPreferences: SharedPreferences
): ViewModel() {

    val weatherImage = ""

    var selectedLocation: String? = sharedPreferences.getString("LOCATION_NAME",
        Context.MODE_PRIVATE.toString() )

    private val _weather: MutableLiveData<ResponseState<WeatherResponseItem>> =
        MutableLiveData(ResponseState.LOADING)
    val weather: LiveData<ResponseState<WeatherResponseItem>> get() = _weather


    fun getWeather(location: String? = selectedLocation) {
        viewModelScope.launch(ioDispatcher) {
            location?.let {
                appRepository.getWeather(it).collect {
                    _weather.postValue(it)

                    Log.d(TAG, "getWeatherViewModel: location = ${location}  ")
                }
            }
        }
    }
}
