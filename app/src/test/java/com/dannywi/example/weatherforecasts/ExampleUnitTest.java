package com.dannywi.example.weatherforecasts;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testWeatherAPI() {
        String st;
        try {
            st = WeatherAPI.getWeather("400040");
        } catch (IOException e) {
            st = e.toString();
        }
        System.out.println("TEST: \n" + st);
    }
}