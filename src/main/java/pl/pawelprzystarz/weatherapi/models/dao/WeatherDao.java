package pl.pawelprzystarz.weatherapi.models.dao;

import pl.pawelprzystarz.weatherapi.models.WeatherModel;

import java.util.List;

public interface WeatherDao {
    void addWeather(WeatherModel model);
    List<WeatherModel> getAllWeatherData(String city);
    List<String> getCities();
}
