package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class AutoFill {

    private String apiKey = "pk.eyJ1IjoibGJhcmUiLCJhIjoiY2szYWhqaTc5MGUzdjNlb2NrbGlyMHdyMyJ9.v_XrJlAoxgK52CAEQoaEsw";
    private String query;
    private JsonElement locationJSE;
    private ArrayList<String> coordinateResults, locationResults;
    private HashMap<String,String> map, map1;


    public AutoFill(String s){
        try {
            query = URLEncoder.encode(s, "utf-8");
            locationFetch();
            storeForecastData();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void locationFetch()
    {
        String mapUrl = "";
        if (!query.matches("[0-9]+")) {
            query = query.replace(" ", "%20");
            mapUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" +
                    query +
                    ".json?access_token=" +
                    apiKey +
                    "&limit=5&types=place";
        }
        else {
            query = query.replace(" ", "");
            mapUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" +
                    query +
                    ".json?access_token=" +
                    apiKey +
                    "&limit=5&types=postcode";
        }

        try
        {
            URL url = new URL(mapUrl);
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

    public JsonArray getLocationResults(){
        return locationJSE.getAsJsonObject()
                .get("features").getAsJsonArray();
    }

    public void storeForecastData(){
        int day = 0;
        int resultsLength = getLocationResults().size();
        coordinateResults = new ArrayList<String>();
        locationResults = new ArrayList<String>();
        map = new HashMap<String,String>();
        map1 = new HashMap<String,String>();

        // store data from each day into their own ArrayList
        for (int i = 0; i < resultsLength; i++) {
            String coordinates = getLocationResults()
                            .get(i).getAsJsonObject()
                            .get("geometry").getAsJsonObject()
                            .get("coordinates").getAsJsonArray()
                            .get(1).getAsString() +
                    "," +
                            getLocationResults()
                            .get(i).getAsJsonObject()
                            .get("geometry").getAsJsonObject()
                            .get("coordinates").getAsJsonArray()
                            .get(0).getAsString();

            String locations = getLocationResults()
                            .get(i).getAsJsonObject()
                            .get("place_name").getAsString();

            coordinateResults.add(coordinates);
            locationResults.add(locations);
            map.put(locationResults.get(i), coordinateResults.get(i));
            map1.put(coordinateResults.get(i), locationResults.get(i));
            day++;
        }
    }

    public ArrayList<String> getLocationResultsArray() {
        return locationResults;
    }

    public ArrayList<String> getCoordinateResults() {
        return coordinateResults;
    }

    public HashMap<String,String> getMap(){
        return map;
    }

    public static void main(String[] args) {
        AutoFill a = new AutoFill("Interlaken");
        System.out.println(a.getLocationResultsArray());
        System.out.println(a.getCoordinateResults());
    }


}
