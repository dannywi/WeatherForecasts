package com.dannywi.example.weatherforecasts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

class WeatherForecast {
    final Location location;
    final List<Forecast> forecastList = new ArrayList<>();
    final Description description;

    WeatherForecast(JSONObject jsonObject) throws JSONException {
        JSONObject locationObject = jsonObject.getJSONObject("location");
        location = new Location(locationObject);

        JSONArray forecastObjects = jsonObject.getJSONArray("forecasts");
        for (int i = 0; i < forecastObjects.length(); ++i) {
            forecastList.add(new Forecast(forecastObjects.getJSONObject(i)));
        }

        description = new Description(jsonObject.getJSONObject("description"));
    }

    public class Location {
        final String area;
        final String prefecture;
        final String city;

        Location(JSONObject jsonObject) throws JSONException {
            area = jsonObject.getString("area");
            prefecture = jsonObject.getString("prefecture");
            city = jsonObject.getString("city");
        }
    }

    class Forecast {
        String date;
        String dateLabel;
        String telop;
        Image image;
        Temperature temperature;

        Forecast(JSONObject jsonObject) throws JSONException {
            date = jsonObject.getString("date");
            dateLabel = jsonObject.getString("dateLabel");
            telop = jsonObject.getString("telop");
            image = new Image(jsonObject.getJSONObject("image"));
            temperature = new Temperature(jsonObject.getJSONObject("temperature"));
        }
    }

    public class Image {
        final String title;
        final String link;
        final String url;
        final int width;
        final int height;

        Image(JSONObject jsonObject) throws JSONException {
            title = jsonObject.getString("title");
            link = jsonObject.has("link") ? jsonObject.getString("link") : null;
            url = jsonObject.getString("url");
            width = jsonObject.getInt("width");
            height = jsonObject.getInt("height");
        }
    }

    public static class Temperature {
        final Value min;
        final Value max;

        Temperature(final JSONObject jsonObject) throws JSONException {
            min = new Value(jsonObject, "min");
            max = new Value(jsonObject, "max");
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            Consumer<Value> fn = v -> sb.append(v.celsius == null ? " - " : v.celsius);

            fn.accept(min);
            sb.append(" / ");
            fn.accept(max);

            return sb.toString();
        }

        static class Value {
            final String celsius;
            final String fahrenheit;

            Value(JSONObject jsonObject, String minMax) throws JSONException {
                if (jsonObject.isNull(minMax)) {
                    celsius = null;
                    fahrenheit = null;
                } else {
                    JSONObject info = jsonObject.getJSONObject(minMax);
                    celsius = info.getString("celsius");
                    fahrenheit = info.getString("fahrenheit");
                }
            }
        }
    }

    public class Description {
        final String value;
        Description(JSONObject jsonObject) throws JSONException {
            value = jsonObject.getString("text");
        }
    }
}
