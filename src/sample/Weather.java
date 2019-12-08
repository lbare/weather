package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class Weather {
    private String location;
    private JsonElement locationJSE, forecastJSE, tempJSE;
    private String[] avgTempF, avgTempC, humidity, conditions, icon, date;
    private Image radarImage;

    // constructor for getting weather from user's IP
    public Weather() {
        locationAutoFetch();
        forecastAutoFetch();
        storeForecastData();
        radarImageFetch();
    }

    // constructor for getting weather from user's input
    public Weather(String input) {
        try {
            location = URLEncoder.encode(input, "utf-8"); // sets location to user input
            locationFetch();
            forecastFetch();
            storeForecastData();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // fetch location weather JSON using user's input location and store in forecastJSE
    public void locationFetch()
    {
        String weatherUrl = "http://api.aerisapi.com/observations/" +
                location +
                "?client_id=" +
                APIKeys.ClientID +
                "&client_secret=" +
                APIKeys.ClientKey; // store String with input location

        try
        {
            URL url = new URL(weatherUrl); // generate URL from String
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            locationJSE = JsonParser.parseReader(isr); // turn read JSON into JSE
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

    // fetch forecast JSON using user's input location and store in forecastJSE
    public void forecastFetch()
    {

        String weatherUrl = "http://api.aerisapi.com/forecasts/"
                + location
                + "?client_id="
                + APIKeys.ClientID
                + "&client_secret="
                + APIKeys.ClientKey; // store String with input location

        try
        {
            URL url = new URL(weatherUrl); // generate URL from String
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            forecastJSE = JsonParser.parseReader(isr); // turn read JSON into JSE
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

    // fetch location weather JSON using user's input location and store in forecastJSE
    public void locationAutoFetch()
    {
        String weatherUrl = "http://api.aerisapi.com/observations/:auto?client_id="
                + APIKeys.ClientID
                + "&client_secret="
                + APIKeys.ClientKey;

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

    // fetch forecast JSON using user's input location and store in forecastJSE
    public void forecastAutoFetch()
    {
        String weatherUrl = "http://api.aerisapi.com/forecasts/:auto?client_id="
                + APIKeys.ClientID
                + "&client_secret="
                + APIKeys.ClientKey;

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

    public void radarImageFetch()
    {
        String weatherUrl = "https://maps.aerisapi.com/"
                + APIKeys.ClientID
                + "_"
                + APIKeys.ClientKey
                + "/flat,radar,counties,interstates,admin-cities/256x256/"
                + "Rocklin,CA"
                + ","
                + "7"
                + "/current.png";

        try
        {
            URL url = new URL(weatherUrl);
            BufferedImage buffImage = ImageIO.read(url);
            radarImage = SwingFXUtils.toFXImage(buffImage,null);
        }
        catch (java.net.MalformedURLException mue)
        {
            System.out.println("Malformed URL");
            mue.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getRadarImage(){
        return radarImage;
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
        date = new String[7];

        // store data from each day into their own array
        while (day < 7) {
            avgTempF[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("avgTempF").getAsString()
                    + "°";

            avgTempC[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("avgTempC").getAsString()
                    + "°";

            humidity[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("humidity").getAsString()
                    + "%";

            conditions[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("weather").getAsString();

             String temp = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("icon").getAsString()
                     .replace("1","");;
            icon[day] = iconSort(temp);

            date[day] = getForecastData()
                    .get(day).getAsJsonObject()
                    .get("validTime").getAsString()
                    .substring(5,10).replace('-','/');


            day++;
        }
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
        String temp = Integer.toString(num) + "°C";
        return temp;
    }

    public String getTemperatureF()
    {
        int num = getLocationData("ob").getAsJsonObject()
                .get("tempF").getAsInt();
        String temp = Integer.toString(num) + "°F";
        return temp;
    }

    public String getMaxF(){
        String temp = getForecastData()
                .get(0).getAsJsonObject()
                .get("maxTempF").getAsString() + "°";
        return temp;
    }

    public String getMinF(){
        String temp = getForecastData()
                .get(0).getAsJsonObject()
                .get("minTempF").getAsString() + "°";
        return temp;
    }

    public String getMaxC(){
        String temp = getForecastData()
                .get(0).getAsJsonObject()
                .get("maxTempC").getAsString() + "°";
        return temp;
    }

    public String getMinC(){
        String temp = getForecastData()
                .get(0).getAsJsonObject()
                .get("minTempC").getAsString() + "°";
        return temp;
    }

    public String getWeather()
    {
        String weather = getLocationData("ob").getAsJsonObject()
                .get("weather").getAsString();
        return weather;
    }

    public String getHumidity()
    {
        String humidity = getLocationData("ob").getAsJsonObject()
                .get("humidity").getAsString() + "%";
        return humidity;
    }

    public String getWindSpeedMPH()
    {
        String humidity = getLocationData("ob").getAsJsonObject()
                .get("windSpeedMPH").getAsString() + " MPH";
        return humidity;
    }

    public String getWindSpeedKPH()
    {
        String humidity = getLocationData("ob").getAsJsonObject()
                .get("windSpeedKPH").getAsString() + " KPH";
        return humidity;
    }

    public String getVisibilityMI()
    {
        String visibility = getLocationData("ob").getAsJsonObject()
                .get("visibilityMI").getAsString();
        if (visibility.indexOf('.') == 2) {
            visibility = visibility.substring(0,2) + " MI";
        }
        else if (visibility.indexOf('.') == 1) {
            visibility = visibility.substring(0,3) + " MI";
        }
        else {
            visibility += " MI";
        }
        return visibility;
    }

    public String getVisibilityKM()
    {
        String visibility = getLocationData("ob").getAsJsonObject()
                .get("visibilityKM").getAsString();
        if (visibility.indexOf('.') == 2) {
            visibility = visibility.substring(0,2) + " KM";
        }
        else if (visibility.indexOf('.') == 1) {
            visibility = visibility.substring(0,3) + " KM";
        }
        else {
            visibility += " KM";
        }
        return visibility;
    }

    public String getWindDirection()
    {
        String direction = getLocationData("ob").getAsJsonObject()
                .get("windDir").getAsString() + ".png";
        return direction;
    }

    public String iconSort(String icon){
        switch (icon) {
            case "am_pcloudy.png":
            case "mcloudy.png":
            case "mcloudyw.png":
            case "pcloudy.png":
            case "pcloudyw.png":
            case "pm_pcloudy.png":
                icon = "pc1.png";
                break;
            case "am_tstorm.png":
            case "chancetstorm.png":
            case "chancetstormn.png":
            case "mcloudyt.png":
            case "mcloudytn.png":
            case "mcloudytw.png":
            case "mcloudytwn.png":
            case "pcloudyt.png":
            case "pcloudytn.png":
            case "pcloudytw.png":
            case "pcloudytwn.png":
            case "pm_tstorm.png":
            case "tstorm.png":
            case "tstormn.png":
            case "tstormsw.png":
            case "tstormswn.png":
            case "tstormw.png":
            case "tstormwn.png":
                icon = "t1.png";
                break;
            case "cloudy.png":
            case "cloudyw.png":
            case "dust.png":
            case "fog.png":
            case "smoke.png":
            case "wind.png":
                icon = "c1.png";
                break;
            case "am_snowshowers.png":
            case "blizzard.png":
            case "blizzardn.png":
            case "blowingsnow.png":
            case "blowingsnown.png":
            case "flurries.png":
            case "flurriesn.png":
            case "flurriesw.png":
            case "flurrieswn.png":
            case "mcloudys.png":
            case "mcloudysfn.png":
            case "mcloudysfw.png":
            case "mcloudysfwn.png":
            case "mcloudysn.png":
            case "mcloudysw.png":
            case "mcloudyswn.png":
            case "pcloudys.png":
            case "pcloudysf.png":
            case "pcloudysfn.png":
            case "pcloudysfw.png":
            case "pcloudysfwn.png":
            case "pcloudysn.png":
            case "pcloudyswn.png":
            case "pm_snowshowers.png":
            case "rainandsnow.png":
            case "rainandsnown.png":
            case "raintosnow.png":
            case "raintosnown.png":
            case "sleet.png":
            case "sleetn.png":
            case "sleetsnow.png":
            case "snow.png":
            case "snowshowers.png":
            case "snowshowersn.png":
            case "snowshowersw.png":
            case "snowshowerswn.png":
            case "snoww.png":
            case "snowwn.png":
            case "snown.png":
                icon = "sn1.png";
                break;
            case "clearn.png":
            case "clearwn.png":
            case "cloudyn.png":
            case "cloudywn.png":
            case "fairn.png":
            case "fairwn.png":
            case "fogn.png":
            case "hazyn.png":
            case "mcloudyn.png":
            case "mcloudywn.png":
            case "pcloudyn.png":
            case "pcloudywn.png":
            case "smoken.png":
            case "sunnyn.png":
            case "sunnywn.png":
                icon = "n1.png";
                break;
            case "am_pcloudyr.png":
            case "am_showers.png":
            case "drizzle.png":
            case "drizzlef.png":
            case "drizzlen.png":
            case "fdrizzle.png":
            case "fdrizzlen.png":
            case "freezingrain.png":
            case "freezingrainn.png":
            case "mcloudyr.png":
            case "mcloudyrn.png":
            case "mcloudyrw.png":
            case "mcloudyrwn.png":
            case "pcloudyr.png":
            case "pcloudyrn.png":
            case "pcloudyrw.png":
            case "pcloudyrwn.png":
            case "pm_pcloudyr.png":
            case "pm_showers.png":
            case "rain.png":
            case "rainn.png":
            case "rainw.png":
            case "showers.png":
            case "showersn.png":
            case "showersw.png":
            case "snowtorain.png":
            case "snowtorainn.png":
            case "wintrymix.png":
            case "wintrymixn.png":
                icon = "r1.png";
                break;
            case "clear.png":
            case "clearw.png":
            case "fair.png":
            case "fairw.png":
            case "hazy.png":
            case "sunny.png":
            case "sunnyw.png":
                icon = "s1.png";
                break;
        }
        return icon;
    }

    public String getIcon()
    {
        String icon = getLocationData("ob")
                .getAsJsonObject()
                .get("icon").getAsString();
        return iconSort(icon);
    }

    public String getCityState()
    {
        String city = getLocationData("place").getAsJsonObject()
                .get("name").getAsString();
        if (city.indexOf('/') > 0){
            city = city.substring(0, city.indexOf('/'));
        }
        String state = getLocationData("place").getAsJsonObject()
                .get("state").getAsString().toUpperCase(); // capitalize State
        if (state.equals("")) {
            state = getLocationData("place").getAsJsonObject()
                    .get("country").getAsString().toUpperCase();
        }
        String cityState = city + "," + " " + state;
        cityState = capitalizeWord(cityState);
        return cityState;
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

    public String getDate(int day){
        return date[day];
    }

    public void storeJsonTest(){
        try
        {
            URL url = new URL("file:JSON/CurrentDay.json"); // generate URL from String
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            URL url1 = new URL("file:JSON/Forecasts.json"); // generate URL from String
            InputStream is1 = url1.openStream();
            InputStreamReader isr1 = new InputStreamReader(is1);

            locationJSE = JsonParser.parseReader(isr); // turn read JSON into JSE
            forecastJSE = JsonParser.parseReader(isr1); // turn read JSON into JSE
        }
        catch (java.io.IOException ioe) {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
    }

    public String capitalizeWord(String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }

    public static void main(String[] args)
    {
        Weather w = new Weather("Rocklin,CA");
        w.radarImageFetch();
    }
}
