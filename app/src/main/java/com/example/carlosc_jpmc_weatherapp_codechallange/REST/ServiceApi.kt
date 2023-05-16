package com.example.carlosc_jpmc_weatherapp_codechallange.REST

import com.example.carlosc_jpmc_weatherapp_codechallange.Model.WeatherResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET(WEATHER)
    suspend fun getWeather(
        @Query("q") city: String? =  null,
        @Query("appid") appid: String = "77b0ca3ff08c890e2c2c4e97d51bac02",
        @Query("units") units: String? = "imperial"
    ): Response<WeatherResponseItem>


    companion object{

        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val WEATHER = "weather"
        const val BASE_IMG_URL = "https://openweathermap.org/img/wn/"
    }


}

//https://api.openweathermap.org/data/2.5/weather?q=Austin&appid=77b0ca3ff08c890e2c2c4e97d51bac02
//https://openweathermap.org/img/wn/10d@2x.png