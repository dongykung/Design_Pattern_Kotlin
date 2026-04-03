package org.example.proxy

interface WeatherService {
    fun getWeather(city: String): String
}

class RealWeatherService : WeatherService {
    override fun getWeather(city: String): String {
        println("API 호출 중")
        return "$city 추움"
    }
}

class CachingWeatherService(
    private val real: WeatherService
): WeatherService {
    private val cache = mutableMapOf<String, String>()

    override fun getWeather(city: String): String {
        return cache.getOrPut(city) {
            real.getWeather(city)
        }
    }
}