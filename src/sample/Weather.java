package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;


public class Weather {
    private String location;
    private JsonElement locationJSE, forecastJSE;
    private String[] avgTempF, avgTempC, humidity, conditions, icon;
    private String clientID = "jqI4SN5g22BSyrI7rBIFb";
    private String clientSecret = "YqPdilijMvTmHzQ01vEGvbWo95iUmIFaw7L47fXR";


    public Weather() {
        locationAutoFetch();
        forecastAutoFetch();
    }

    public Weather(String input) {
        try {
            location = URLEncoder.encode(input, "utf-8");
            locationFetch();
            forecastFetch();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void locationFetch()
    {

        String weatherUrl = "http://api.aerisapi.com/observations/" +
                location +
                "?client_id=" +
                clientID +
                "&client_secret=" +
                clientSecret;

        try
        {
            URL url = new URL(weatherUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            locationJSE = JsonParser.parseReader(isr);
        }
        catch (java.net.MalformedURLException mue)
        {
            System.out.println("Malformed URL");
            mue.printStackTrace();
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
    }

    public void forecastFetch()
    {

        String weatherUrl = "http://api.aerisapi.com/forecasts/"
                + location
                + "?client_id="
                + clientID
                + "&client_secret="
                + clientSecret;

        try
        {
            URL url = new URL(weatherUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            forecastJSE = JsonParser.parseReader(isr);
        }
        catch (java.net.MalformedURLException mue)
        {
            System.out.println("Malformed URL");
            mue.printStackTrace();
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
    }

    public void locationAutoFetch()
    {
        String weatherUrl = "http://api.aerisapi.com/observations/:auto?client_id="
                + clientID
                + "&client_secret="
                + clientSecret;

        try
        {
            URL url = new URL(weatherUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            locationJSE = JsonParser.parseReader(isr);
        }
        catch (java.net.MalformedURLException mue)
        {
            System.out.println("Malformed URL");
            mue.printStackTrace();
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
    }

    public void forecastAutoFetch()
    {
        String weatherUrl = "http://api.aerisapi.com/forecasts/:auto?client_id="
                + clientID
                + "&client_secret="
                + clientSecret;

        try
        {
            URL url = new URL(weatherUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            forecastJSE = JsonParser.parseReader(isr);
        }
        catch (java.net.MalformedURLException mue)
        {
            System.out.println("Malformed URL");
            mue.printStackTrace();
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
    }

    public JsonArray getForecastData(){
        return forecastJSE.getAsJsonObject()
                .get("response").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("periods").getAsJsonArray();
    }

    public void storeForecastData(){
        int day = 0;
        avgTempF = new String[7];
        avgTempC = new String[7];
        humidity = new String[7];
        conditions = new String[7];
        icon = new String[7];

        // store data from each day into their own array
        while (day < 7) {
            avgTempF[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("avgTempF").getAsString();

            avgTempC[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("avgTempC").getAsString();

            humidity[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("humidity").getAsString();

            conditions[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("weather").getAsString();

            icon[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("icon").getAsString();

            day++;
        }
    }

    public String getAvgTempF(int day){
        return avgTempF[day];
    }

    public String getAvgTempC(int day){
        return avgTempC[day];
    }

    public String getHumidity(int day){
        return humidity[day];
    }

    public String getConditions(int day){
        return conditions[day];
    }

    public String getIcon(int day){
        return icon[day];
    }

    public JsonObject getLocationData(String key){
        return locationJSE.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get(key).getAsJsonObject();
    }

    public String getTemperatureC()
    {
        int num = getLocationData("ob").getAsJsonObject()
                .get("tempC").getAsInt();
        String temp = Integer.toString(num);
        return temp;
    }

    public String getTemperatureF()
    {
        int num = getLocationData("ob").getAsJsonObject()
                .get("tempF").getAsInt();
        String temp = Integer.toString(num);
        return temp;
    }

    public String getWeather()
    {
        String weather = getLocationData("ob").getAsJsonObject()
                .get("weather").getAsString();
        return weather;
    }

    public String getIcon()
    {
        String icon = getLocationData("ob")
                .getAsJsonObject()
                .get("icon").getAsString();

        return icon;
    }

    public String getCityState()
    {
        String cityState = getLocationData("place").getAsJsonObject()
                .get("name").getAsString();
        cityState = cityState.substring(0, 1).toUpperCase() + cityState.substring(1);
        cityState = cityState + "," + " " + getLocationData("place").getAsJsonObject()
                .get("state").getAsString().toUpperCase();
        return cityState;
    }

    public static void main(String[] args)
    {
        Weather w = new Weather("42.1307,-70.9162");
        w.storeForecastData();
    }
}
