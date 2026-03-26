package com.sd.laborator.controllers

import com.sd.laborator.interfaces.BlackListInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.LocationData
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.util.Locale

@Controller
open class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var blackList: BlackListInterface

    @RequestMapping("/getforecast/{locationName}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable locationName: String): String {
        // se incearca preluarea WOEID-ului locaţiei primite in URL
        val currentLocation = Locale.getDefault()
        var country = currentLocation.displayCountry

//        country = "Russia"

        //verificam daca tara noastra e pe blackList
        if (blackList.isZoneBlackListed(country)) {
            return "Nu ai voie sa accesezi date meteo deoarece esti din tara $country"
        }

        ///Luam obiect ca LocationData
        val location: LocationData = locationSearchService.getLocationId(locationName)

        //verificam daca tara cautata e pe blackList
        if (blackList.isZoneBlackListed(location.country)){
            return "Nu ai voie sa accesezi date meteo din aceeasta tara ${location.country}"
        }

        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (location.city == "") {
            return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$locationName\"!"
        }

        //Pe baza locatiei -> latitudine si longitudine luam datele necesare
        // încapsulate într-un obiect POJO
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(location)

        // fiind obiect POJO, funcţia toString() este suprascrisă pentru o afişare mai prietenoasă
        //test de locatie
        return rawForecastData.toString()
    }
}