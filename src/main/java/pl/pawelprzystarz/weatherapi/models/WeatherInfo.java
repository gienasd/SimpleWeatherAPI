package pl.pawelprzystarz.weatherapi.models;

public class WeatherInfo {
    private double temp;
    private double pressure;
    private String city;

    public WeatherInfo(double temp, double pressure, String city) {
        this.temp = temp;
        this.pressure = pressure;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
