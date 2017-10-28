package pl.pawelprzystarz.weatherapi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.pawelprzystarz.weatherapi.models.IWeatherObserver;
import pl.pawelprzystarz.weatherapi.models.WeatherInfo;
import pl.pawelprzystarz.weatherapi.models.WeatherModel;
import pl.pawelprzystarz.weatherapi.models.dao.WeatherDao;
import pl.pawelprzystarz.weatherapi.models.dao.impl.WeatherDaoImpl;
import pl.pawelprzystarz.weatherapi.models.services.WeatherService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, IWeatherObserver {

    @FXML
    TextField textCity;

    @FXML
    Button buttonSend, buttonCharts;

    @FXML
    Label labelTemp;

    @FXML
    ProgressIndicator progressIndi;

    private WeatherService weatherService = WeatherService.getService();
    private WeatherDao weatherDao = new WeatherDaoImpl();

    public void initialize(URL location, ResourceBundle resources) {
        progressIndi.setVisible(false);
        labelTemp.setVisible(false);
        weatherService.registerObserver(this);

        buttonSend.setOnMouseClicked(e -> {
            if(!textCity.getText().isEmpty()) {
                progressIndi.setVisible(true);
                labelTemp.setVisible(false);
                weatherService.makeRequest(textCity.getText());
                textCity.clear();
            }
        });

        textCity.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                progressIndi.setVisible(true);
                labelTemp.setVisible(false);
                weatherService.makeRequest(textCity.getText());
                textCity.clear();
         }
       });

        buttonCharts.setOnMouseClicked(e -> goToCharts());
    }

    private void goToCharts() {
        Stage stage = (Stage) buttonCharts.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chartView.fxml"));
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWeatherUpdate(WeatherInfo info) {
        labelTemp.setText("Temp: " + info.getTemp() + " | Ciśnienie: " + info.getPressure());
        progressIndi.setVisible(false);
        labelTemp.setVisible(true);

        weatherDao.addWeather(new WeatherModel(info));
    }
}
