package com.example.pogoda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherApp extends Application {
    private static final String API_KEY = "5e25a403566d2021cb79b3fd3fd37cc5";
    private Label weatherInfo;
    private ImageView weatherIcon;

    @Override
    public void start(Stage stage) {
        Text title = new Text("APLIKACJA POGODOWA");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setStyle("-fx-font-size: 30px;");

        TextField cityField = new TextField();
        cityField.setPromptText("Wpisz nazwę miasta");

        Button searchButton = new Button("Wyszukaj");
        weatherInfo = new Label();
        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(100);
        weatherIcon.setFitWidth(100);

        searchButton.setOnAction(event -> fetchWeather(cityField.getText()));

        Button closeButton = new Button("Zamknij");
        closeButton.setOnAction(event -> stage.close());

        VBox layout = new VBox(15, title, cityField, searchButton, weatherIcon, weatherInfo, closeButton);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        VBox closeButtonContainer = new VBox(closeButton);
        closeButtonContainer.setAlignment(Pos.BOTTOM_LEFT);
        layout.getChildren().remove(closeButton);
        layout.getChildren().add(closeButtonContainer);

        Scene scene = new Scene(layout, 500, 600);
        stage.setTitle("API Pogoda");
        stage.setScene(scene);
        stage.show();
    }

    private void fetchWeather(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + API_KEY + "&lang=pl&units=metric";
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");

            InputStream responseStream = connection.getInputStream();
            Scanner scanner = new Scanner(responseStream);
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject json = new JSONObject(response);
            String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
            String iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");
            double temperature = json.getJSONObject("main").getDouble("temp");
            double temperatureFeel = json.getJSONObject("main").getDouble("feels_like");
            double temperatureMin = json.getJSONObject("main").getDouble("temp_min");
            double temperatureMax = json.getJSONObject("main").getDouble("temp_max");
            int pressure = json.getJSONObject("main").getInt("pressure");
            int humidity = json.getJSONObject("main").getInt("humidity");
            int clouds = json.getJSONObject("clouds").getInt("all");
            double windSpeed = json.getJSONObject("wind").getDouble("speed");
            int visib = json.getInt("visibility");
            double visibility = visib / 1000.0;

            String weatherText = "Miasto: " + city + "\n" +
                    "Temperatura: " + temperature + "°C\n" +
                    "Temperatura odczuwalna: " + temperatureFeel + "°C\n" +
                    "Temperatura minimalna: " + temperatureMin + "°C\n" +
                    "Temperatura maksymalna: " + temperatureMax + "°C\n" +
                    "Ciśnienie atmosferyczne: " + pressure + "hPa\n" +
                    "Wilgotność: " + humidity + "%\n" +
                    "Wiatr: " + windSpeed + " m/s\n" +
                    "Zachmurzenie: " + clouds + "%\n" +
                    "Widoczność: " + visibility + "km\n" +
                    "Opis: " + weatherDescription;
            weatherInfo.setText(weatherText);

            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            weatherIcon.setImage(new Image(iconUrl));

        } catch (Exception e) {
            weatherInfo.setText("Nie udało się pobrać danych" + e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
