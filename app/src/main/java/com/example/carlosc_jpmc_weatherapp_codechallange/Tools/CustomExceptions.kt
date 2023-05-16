package com.example.carlosc_jpmc_weatherapp_codechallange.Tools

class NullWeatherException(message: String = "WeatherApi Response is null"): Exception(message)
class FailureResponseException(message: String? = "Response failed"): Exception(message)