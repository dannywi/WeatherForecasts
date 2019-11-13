package com.dannywi.example.weatherforecasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {
    private static final String API_ENDPOINT = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";

    static WeatherForecast getWeather(String cityId) throws JSONException, IOException {
        URL url = new URL(API_ENDPOINT + cityId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
        } finally {
            connection.disconnect();
        }

        JSONObject jsonObject = new JSONObject(sb.toString());
        System.out.println(jsonObject.toString(4));
        return new WeatherForecast(jsonObject);
    }
}
