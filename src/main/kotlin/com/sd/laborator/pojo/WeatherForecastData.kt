package com.sd.laborator.pojo

data class WeatherForecastData (
    var location: String,
    var date: String,
    var weatherState: String,
    var weatherStateIconURL: String,
    var windDirection: Int,
    var windSpeed: Double, // km/h
    var minTemp: Double, // grade celsius
    var maxTemp: Double,
    var currentTemp: Double,
){
    override fun toString(): String {
        var output = "location: $location, date: $date\n"
        output += "weatherState: $weatherState\n"
        output += "\nweather_state_icon_url: $weatherStateIconURL\n"

        output += "\ncurrent_temp: $currentTemp\n"
        output += "\nmin_temp: $minTemp\n"
        output += "\nmax_temp: $maxTemp\n"

        output += "\nwind_direction_10m: $windDirection\n"
        output += "\nwind_speed_10m: $windSpeed\n"
        return output
    }
}
