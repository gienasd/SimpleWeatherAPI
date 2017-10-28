package pl.pawelprzystarz.weatherapi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pl.pawelprzystarz.weatherapi.models.WeatherModel;
import pl.pawelprzystarz.weatherapi.models.dao.WeatherDao;
import pl.pawelprzystarz.weatherapi.models.dao.impl.WeatherDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    @FXML
    BarChart chartTemp;

    @FXML
    ListView<String> listCities;

    @FXML
    Button buttonBack;

    private WeatherDao weatherDao = new WeatherDaoImpl();
    private ObservableList cityItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCities();

        listCities.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                generateChart(newValue);
        });
        
        buttonBack.setOnMouseClicked(e -> backToMainView());
    }

    private void backToMainView() {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateChart(String city) {
        List<WeatherModel> weatherList = weatherDao.getAllWeatherData(city);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(city);
        for (WeatherModel weatherModel : weatherList) {
            series.getData().add(new XYChart.Data<>(weatherModel.getDate().toString(), weatherModel.getTemp() - 273));
        }
        chartTemp.getData().clear();
        chartTemp.getData().add(series);
    }

    private void loadCities() {
         cityItems = FXCollections.observableArrayList(weatherDao.getCities());
         listCities.setItems(cityItems);
    }
}
