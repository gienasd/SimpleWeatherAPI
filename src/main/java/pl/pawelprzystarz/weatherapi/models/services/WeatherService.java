package pl.pawelprzystarz.weatherapi.models.services;

import javafx.application.Platform;
import org.json.JSONObject;
import pl.pawelprzystarz.weatherapi.models.Config;
import pl.pawelprzystarz.weatherapi.models.IWeatherObserver;
import pl.pawelprzystarz.weatherapi.models.Utils;
import pl.pawelprzystarz.weatherapi.models.WeatherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService{

    private static WeatherService ourInstance = new WeatherService();
    public static WeatherService getService() {
        return ourInstance;
    }

    private ExecutorService executorService;

    private WeatherService() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public void makeRequest(String city) {
        Runnable runnable = () -> readJSonData(Utils.makeHttpRequest(Config.APP_BASE_URL + city + "&appid=" + Config.APP_ID), city);
        executorService.execute(runnable);
    }

    private List<IWeatherObserver> observer = new ArrayList<>();

    private void readJSonData(String json, String city){
        JSONObject root = new JSONObject(json);
        JSONObject main = root.getJSONObject("main");

        int pressure = main.getInt("pressure");
        double temp = main.getDouble("temp");

        observer.forEach(s -> {
            Platform.runLater(()->s.onWeatherUpdate(new WeatherInfo(temp, pressure, city)));
        });
    }

    public void registerObserver(IWeatherObserver observer) {
        this.observer.add(observer);
    }
}
