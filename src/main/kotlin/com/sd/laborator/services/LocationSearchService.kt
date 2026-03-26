package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.pojo.LocationData
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


//modificam aplicatia de laborator folosind open-meteo
@Service
open class LocationSearchService : LocationSearchInterface {
    override fun getLocationId(locationName: String): LocationData {
        // codificare parametru URL (deoarece poate conţine caractere speciale)
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())

        // construire obiect de tip URL

        //link-ul nou
        val locationSearchURL = URL("https://geocoding-api.open-meteo.com/v1/search?name=$encodedLocationName&count=1&language=en&format=json")

        // preluare raspuns HTTP (se face cerere GET şi se preia conţinutul răspunsului sub formă de text)
        val rawResponse: String = locationSearchURL.readText()

        // parsare obiect JSON

        //Am creat un Pojo cu latitudinea si longitudinea, pt ca open-meteo pe asta se bazeaza, nu pe locatie
        val responseRootObject = JSONObject(rawResponse)
        if (responseRootObject.has("results")) {
            val firstResult = responseRootObject.getJSONArray("results").getJSONObject(0)
            return LocationData(
                latitude = firstResult.getDouble("latitude"),
                longitude = firstResult.getDouble("longitude"),
                city = firstResult.getString("name"),
                country= firstResult.getString("country")
            )
        }
        //maxima la latitudine e 90, si la long e 180, trimitem ceva mai mare daca nu am gasit locatia
        return LocationData (91.0, 181.0,"", "")
    }
}