public class WeatherHistory {
    private String city;
    private String temperature;
    private String humidity;
    private String wind;
    private String condition;
    private String time;

    public WeatherHistory(String city, String temperature, String humidity,
                          String wind, String condition, String time) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.condition = condition;
        this.time = time;
    }

    public String getCity() { return city; }
    public String getTemperature() { return temperature; }
    public String getHumidity() { return humidity; }
    public String getWind() { return wind; }
    public String getCondition() { return condition; }
    public String getTime() { return time; }
}