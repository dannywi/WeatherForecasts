package com.dannywi.example.weatherforecasts;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ForecastFragment extends Fragment {

    private static final String KEY_CITY_CODE = "key_city_code";

    public static ForecastFragment newInstance(String cityCode) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CITY_CODE, cityCode);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView location;
    private LinearLayout forecastsLayout;
    private ProgressBar progressBar;
    private TextView description;

    public class APITask extends GetWeatherForecastTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(WeatherForecast data) {
            super.onPostExecute(data);
            progressBar.setVisibility(View.GONE);
            if (data != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(data.location.area);
                sb.append(" ");
                sb.append(data.location.prefecture);
                sb.append(" ");
                sb.append(data.location.city);
                location.setText(sb.toString());

                for (WeatherForecast.Forecast forecast : data.forecastList) {
                    View row = View.inflate(getContext(), R.layout.forecast_row, null);

                    TextView date = row.findViewById(R.id.tv_date);
                    date.setText(forecast.dateLabel);

                    TextView telop = row.findViewById(R.id.tv_telop);
                    telop.setText(forecast.telop);

                    TextView temperature = row.findViewById(R.id.tv_temperature);
                    temperature.setText(forecast.temperature.toString());

                    ImageView image = row.findViewById(R.id.iv_weather);
                    ImageLoaderTask task = new ImageLoaderTask();
                    task.execute(new ImageLoaderTask.Request(image, forecast.image.url));

                    forecastsLayout.addView(row);
                }

                description.setText(data.description.value);

            } else if (exception != null) {
                Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, null);

        location = view.findViewById(R.id.tv_location);
        forecastsLayout = view.findViewById(R.id.ll_forecasts);
        progressBar = view.findViewById(R.id.progress);
        description = view.findViewById(R.id.tv_description);
        description.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String cityCode = getArguments().getString(KEY_CITY_CODE);
        new APITask().execute(cityCode);
    }
}
