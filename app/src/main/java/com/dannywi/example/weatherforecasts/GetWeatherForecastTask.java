package com.dannywi.example.weatherforecasts;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

public class GetWeatherForecastTask extends AsyncTask<String, Void, WeatherForecast> {
    Exception exception;

    @Override
    protected WeatherForecast doInBackground(String... params) {
        try {
            return WeatherAPI.getWeather(params[0]);
        } catch (IOException | JSONException e) {
            exception = e;
        }
        return null;
    }
}
