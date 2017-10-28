package pl.pawelprzystarz.weatherapi.models.dao.impl;

import pl.pawelprzystarz.weatherapi.models.MySqlConnector;
import pl.pawelprzystarz.weatherapi.models.WeatherModel;
import pl.pawelprzystarz.weatherapi.models.dao.WeatherDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDaoImpl implements WeatherDao {

    MySqlConnector mySqlConnector = MySqlConnector.getInstance();

    @Override
    public void addWeather(WeatherModel model) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = mySqlConnector.getConnection().prepareStatement(
                    "INSERT INTO weatherMap VALUES(?,?,?,?)"
            );
            preparedStatement.setInt(1,0);
            preparedStatement.setString(2, model.getCity());
            preparedStatement.setFloat(3, model.getTemp());
            preparedStatement.setDate(4, null);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<WeatherModel> getAllWeatherData(String city) {
        List<WeatherModel> weatherList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = mySqlConnector.getConnection().prepareStatement(
                    "SELECT * FROM weatherMap WHERE city = ?"
            );
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                weatherList.add(new WeatherModel( resultSet.getFloat("temp"),
                       resultSet.getDate("date"), resultSet.getString("city")));
            }

            preparedStatement.close();
            return weatherList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getCities() {
        List<String> cityList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = mySqlConnector.getConnection().prepareStatement(
                    "SELECT DISTINCT city FROM weatherMap"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cityList.add(resultSet.getString("city"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }
}
