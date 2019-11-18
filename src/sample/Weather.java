package sample;

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
    JsonElement jse;
    private String clientID = "wQhXMMnxoRV4HNKoRLZrL";
    private String clientSecret = "b7HRgvAwgyo9EOyZtmninJ4WNzAvBzAIOAswkkgv";


    public Weather() {
        autoFetch();
    }

    public Weather(String input) {
        try {
            location = URLEncoder.encode(input, "utf-8");
            locationFetch();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void locationFetch()
    {

        String weatherUrl = "http://api.aerisapi.com/observations/"
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

            jse = JsonParser.parseReader(isr);
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

    public void autoFetch()
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

            jse = JsonParser.parseReader(isr);
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

    public JsonObject getData(String key){
        return jse.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get(key).getAsJsonObject();
    }

    public String getTemperatureC()
    {
        int num = getData("ob").getAsJsonObject()
                .get("tempC").getAsInt();
        String temp = Integer.toString(num);
        return temp;
    }

    public String getTemperatureF()
    {
        int num = getData("ob").getAsJsonObject()
                .get("tempF").getAsInt();
        String temp = Integer.toString(num);
        return temp;
    }

    public String getWeather()
    {
        String weather = getData("ob").getAsJsonObject()
                .get("weather").getAsString();
        return weather;
    }
    public String getIcon()
    {
        String icon = getData("ob")
                .getAsJsonObject()
                .get("icon").getAsString();

        return icon;
    }

    public String getCityState()
    {
        String cityState = getData("place").getAsJsonObject()
                .get("name").getAsString();
        cityState = cityState.substring(0, 1).toUpperCase() + cityState.substring(1);
        cityState = cityState + "," + " " + getData("place").getAsJsonObject()
                .get("state").getAsString().toUpperCase();
        return cityState;
    }

    public static void main(String[] args)
    {

    }
}
