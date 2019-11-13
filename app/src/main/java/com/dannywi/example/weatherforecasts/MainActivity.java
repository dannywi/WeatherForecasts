package com.dannywi.example.weatherforecasts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    // todo: generate options from http://weather.livedoor.com/forecast/rss/primary_area.xml
    private static final String[] CITY_LIST = {
            "130010",   // Tokyo
            "180010",   // Fukui
            "180020",   // Tsuruga
            "270000",   // Osaka
            "014030",   // Obihiro
            "460010",   // Kagoshima
            "473000"    // Miyakojima
    };

    private class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ForecastFragment.newInstance(CITY_LIST[position]);
        }

        @Override
        public int getCount() {
            return CITY_LIST.length;
        }
    }

    private Adapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
