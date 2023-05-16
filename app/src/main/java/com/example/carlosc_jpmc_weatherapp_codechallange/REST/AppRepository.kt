package com.example.carlosc_jpmc_weatherapp_codechallange.REST

import com.example.carlosc_jpmc_weatherapp_codechallange.Model.WeatherResponseItem
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.FailureResponseException
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.NullWeatherException
import com.example.carlosc_jpmc_weatherapp_codechallange.Tools.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface AppRepository {
    fun getWeather(city: String, state: String? = null, country: String?=null): Flow<ResponseState<WeatherResponseItem>>
}

class AppRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
): AppRepository{
    override fun getWeather(city: String, state: String?, country: String?): Flow<ResponseState<WeatherResponseItem>> = flow {
        emit(ResponseState.LOADING)
        try{
            val response = serviceApi.getWeather(city)
            if (response.isSuccessful){
                response.body()?.let{
                    emit(ResponseState.SUCCESS(it))
                }?: throw NullWeatherException()
            }else throw FailureResponseException(response.errorBody()?.string())
        }catch (e: Exception){
            emit(ResponseState.ERROR(e))
        }
    }

}