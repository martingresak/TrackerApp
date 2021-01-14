package com.example.trackerapp;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    private String WeatherCity, WeatherType, WeatherTemp, WeatherIcon;
    private int mCondition;

    public static WeatherData fromJson(JSONObject jsonObject)
    {
        try
        {
            WeatherData weatherD = new WeatherData();
            weatherD.WeatherCity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.WeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.WeatherIcon=updateWeatherIcon(weatherD.mCondition);
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.WeatherTemp=Integer.toString(roundedValue);
            return weatherD;
        }

        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "art_storm";
        }
        else if(condition>=300 && condition<=500)
        {
            return "art_light_rain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "art_rain";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "art_snow";
        }
        else if(condition>=701 && condition<=771)
        {
            return "art_fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "art_light_clouds";
        }
        else if(condition==800)
        {
            return "art_clear";
        }
        else if(condition>=801 && condition<=804)
        {
            return "art_clouds";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "art_storm";
        }
        if(condition==903)
        {
            return "art_snow";
        }
        if(condition==904)
        {
            return "art_clear";
        }
        if(condition>=905 && condition<=1000)
        {
            return "art_storm";
        }

        return "dunno";

    }

    public String getWeatherCity() {
        return WeatherCity;
    }

    public String getWeatherType() {
        return WeatherType;
    }

    public String getWeatherTemp() {
        return WeatherTemp + "°C";
    }

    public String getWeatherIcon() {
        return WeatherIcon;
    }
}
