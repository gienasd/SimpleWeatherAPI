package pl.pawelprzystarz.weatherapi.models;

import java.sql.Date;

public class WeatherModel {
    private float temp;
    private Date date;
    private String city;

    public WeatherModel(Float temp, Date date, String city) {
        this.temp = temp;
        this.date = date;
        this.city = city;
    }

    public WeatherModel(WeatherInfo info){
        this.temp = (float) info.getTemp();
        this.date = new Date(0);
        this.city = info.getCity();
    }

    public float getTemp() {
        return temp;
    }
    public void setTemp(float temp) {
        this.temp = temp;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
