package com.sd.laborator.services

import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.LocationData
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
open class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    override fun getForecastData(location: LocationData): WeatherForecastData {

        //noul link pe care cautam informatiile
        val forecastDataURL = URL("https://api.open-meteo.com/v1/forecast?latitude=${location.latitude}&longitude=${location.longitude}&daily=temperature_2m_max,temperature_2m_min&current=temperature_2m,weather_code,wind_speed_10m,wind_direction_10m,rain")

        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        val currentDataObject = responseRootObject.getJSONObject("current")
        val dailyDataObject = responseRootObject.getJSONObject("daily")
        val minTempArray = dailyDataObject.getJSONArray("temperature_2m_min")
        val maxTempArray = dailyDataObject.getJSONArray("temperature_2m_max")

        // construire şi returnare obiect POJO care încapsulează datele meteo
        return WeatherForecastData(
            location = location.city,
            date = timeService.getCurrentTime(),
            weatherState = currentDataObject.get("weather_code").toString(),
            weatherStateIconURL =
                "https://api.open-meteo.com/v1/forecast?latitude=${location.latitude}&longitude=${location.longitude}&current=temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m&daily=temperature_2m_max,temperature_2m_min",
            windDirection = currentDataObject.getInt("wind_direction_10m"),
            windSpeed = currentDataObject.getDouble("wind_speed_10m"),
            minTemp = minTempArray.getDouble(0),
            maxTemp = maxTempArray.getDouble(0),
            currentTemp = currentDataObject.getDouble("temperature_2m")
        )
    }
}
//https://open-meteo.com/en/docs?latitude=47.1667&longitude=27.6&hourly=&timezone=Europe%2FBerlin&daily=temperature_2m_max,temperature_2m_min&current=temperature_2m,weather_code,wind_speed_10m,wind_direction_10m,rain#location_and_time
//https://api.open-meteo.com/v1/forecast?latitude=47.1667&longitude=27.6&daily=temperature_2m_max,temperature_2m_min&current=temperature_2m,weather_code,wind_speed_10m,wind_direction_10m,rain