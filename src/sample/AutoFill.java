package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class AutoFill {

    private String apiKey = "pk.eyJ1IjoibGJhcmUiLCJhIjoiY2szYWhqaTc5MGUzdjNlb2NrbGlyMHdyMyJ9.v_XrJlAoxgK52CAEQoaEsw";
    private String query;
    private JsonElement mapJSE;
    private ArrayList<String> searchResults;


    public AutoFill(String s){
        query = s;
        fetch();
    }

    public void fetch()
    {

        String mapUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" +
                query +
                ".json?access_token=" +
                apiKey +
                "&limit=5&types=place";

        try
        {
            URL url = new URL(mapUrl);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            mapJSE = JsonParser.parseReader(isr);
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

    public JsonArray getSearchResults(){
        return mapJSE.getAsJsonObject()
                .get("features").getAsJsonArray();
    }

    public void storeForecastData(){
        int day = 0;
        int resultsLength = getSearchResults().size();
        searchResults = new ArrayList<String>();

        // store data from each day into their own array
        for (int i = 0; i < resultsLength; i++) {
            String store = getSearchResults()
                    .get(i).getAsJsonObject()
                    .get("geometry").getAsJsonObject()
                    .get("coordinates").getAsJsonArray()
                    .get(1).getAsString() +
                    "," +
                    getSearchResults()
                    .get(i).getAsJsonObject()
                    .get("geometry").getAsJsonObject()
                    .get("coordinates").getAsJsonArray()
                    .get(0).getAsString();

            searchResults.add(store);
            day++;
        }
    }

    public static void main(String[] args) {

    }


}
