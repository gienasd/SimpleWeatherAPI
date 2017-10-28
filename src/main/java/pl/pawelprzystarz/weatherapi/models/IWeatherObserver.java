package pl.pawelprzystarz.weatherapi.models;

public interface IWeatherObserver {
    void onWeatherUpdate(WeatherInfo info);
}
