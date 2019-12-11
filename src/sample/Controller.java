package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import sierra.AsyncTask;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField locationInput;

    @FXML
    Pane homePane, infoPane;

    @FXML
    DialogPane dialogPane;

    @FXML
    ComboBox resultsBox;

    @FXML
    Label tempLabel, weatherLabel, locationLabel, day0temp, day1temp, day2temp, day3temp, day4temp, day5temp, day6temp, humidityLabel,
            day0Label, day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, currentDateLabel, minLabel, maxLabel, windLabel,
            visibilityLabel, locationErrorLabel, timeUpdated, day0RainChance, day1RainChance, day2RainChance, day3RainChance, day4RainChance,
            day5RainChance, day6RainChance, elevationLabel, sunriseLabel, sunsetLabel, pressureLabel, precipLabel, feelsLikeLabel;

    @FXML
    ImageView imageView, day0view, day1view, day2view, day3view, day4view, day5view, day6view, bgImage, arrowImage, tempImage, windDirection,
               radarImageView, infoIcon;

    private int buttonClicked;
    private boolean tempState, homeState;
    private HashMap<String,String> map;
    private String selection, zoomLevel;
    private Image Icon1, day0icon, day1icon, day2icon, day3icon, day4icon, day5icon, day6icon;
    private Weather w1;
    private ObservableList<String> searchResults = FXCollections.observableArrayList("");
    public String currentLocation;

    public void handleGoButton(){
        AsyncTask t = new locationSearchBackground();
        t.execute("test");
        buttonClicked = 0;
    }

    public void myLocation(){
        AsyncTask t = new myLocationBackground();
        t.execute("test");
        buttonClicked = 1;
    }

    public void autoFillUpdate(KeyEvent e){
        if ((locationInput.getText().length()) > 2) {
            AsyncTask t = new autofillBackground();
            t.execute("test");
        }
    }

    public void toggleButton(){
        AsyncTask a = new toggleBackground();
        a.execute("test");
        tempState = !tempState;
    }

    public void homeToggle(){
        toggleToHomePane();
        toggleToInfoPane();
        homeState = !homeState;
    }

    public void clearSearch() {
        locationInput.clear();
    }

    public void checkComboBox(ActionEvent e){
        if (resultsBox.getValue() != null) {
            handleGoButton();
        }
    }

    public void closeWindow(){
        System.exit(1);
    }

    public class locationSearchBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query) {
            zoomLevel = "7";
            selection = map.get(resultsBox.getValue()); // gets selected value from ComboBox
            Weather w = new Weather(selection,zoomLevel); // creates new Weather object using the selected location as the parameter
            currentLocation = selection;
            return w;
        }
        public void onPostExecute(Weather w) {
            try {
                displayHome(w);
                displayInfo(w);
                setUpdatedTime(w);
            }
            catch (IllegalStateException ie) {
                dialogPane.setVisible(true);
                locationErrorLabel.setText(resultsBox.getValue().toString());
            }
        }
    }

    public class myLocationBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query){
            zoomLevel = "7";
            Weather w = new Weather(zoomLevel); // creates new Weather object using the selected location as the parameter
            return w;
        }
        public void onPostExecute(Weather w){
            displayHome(w);
            displayInfo(w);
            setUpdatedTime(w);
        }
    }

    public class autofillBackground extends AsyncTask<String, AutoFill> {
        public AutoFill doInBackground(String query) {
            AutoFill a = new AutoFill(locationInput.getText());
            return a;
        }
        public void onPostExecute(AutoFill a){
            map = new HashMap<String, String>();
            map = a.getMap();
            searchResults = FXCollections.observableArrayList(a.getLocationResultsArray());
            resultsBox.setItems(searchResults);
            resultsBox.show();
        }
    }

    public class zoomBackground extends AsyncTask<String, Weather> {
        public Weather doInBackground(String zoomLevel) {
            Weather w = new Weather(currentLocation,zoomLevel);
            return w;
        }
        public void onPostExecute(Weather w){
            displayInfo(w);
        }
    }

    public class toggleBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query){
            if (buttonClicked == 0) {
                w1 = new Weather(currentLocation,zoomLevel);
            }
            else {
                w1 = new Weather(zoomLevel);
            }
            return w1;
        }
        public void onPostExecute(Weather w){
            toggleToC(w1);
            toggleToF(w1);
        }
    }

    public void displayHome(Weather w) {
        tempState = true;
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            minLabel.setText(w.getMinF());
            maxLabel.setText(w.getMaxF());
            windLabel.setText(w.getWindSpeedMPH());
            visibilityLabel.setText(w.getVisibilityMI());
        }
        else {
            tempLabel.setText(w.getTemperatureC());
            minLabel.setText(w.getMinC());
            maxLabel.setText(w.getMaxC());
            windLabel.setText(w.getWindSpeedKPH());
            visibilityLabel.setText(w.getVisibilityKM());
            tempImage.setImage(new Image("file:icons/cel.png"));
        }
        weatherLabel.setText(w.getWeather());
        windDirection.setImage(new Image("file:arrows/" + w.getWindDirection()));
        locationLabel.setText(w.getCityState());
        humidityLabel.setText(w.getHumidity());
        forecastLabels(w);

        Icon1 = new Image("file:icons/" + w.getIcon());
        imageView.setImage(Icon1);
        currentDateLabel.setText(w.getDate(0));
    }

    public void forecastLabels(Weather w){
        if (tempState){
            day0temp.setText(w.getAvgTempF(0));
            day1temp.setText(w.getAvgTempF(1));
            day2temp.setText(w.getAvgTempF(2));
            day3temp.setText(w.getAvgTempF(3));
            day4temp.setText(w.getAvgTempF(4));
            day5temp.setText(w.getAvgTempF(5));
            day6temp.setText(w.getAvgTempF(6));
        }
        else {
            day0temp.setText(w.getAvgTempC(0));
            day1temp.setText(w.getAvgTempC(1));
            day2temp.setText(w.getAvgTempC(2));
            day3temp.setText(w.getAvgTempC(3));
            day4temp.setText(w.getAvgTempC(4));
            day5temp.setText(w.getAvgTempC(5));
            day6temp.setText(w.getAvgTempC(6));
        }
        day0Label.setText(w.getDate(0));
        day1Label.setText(w.getDate(1));
        day2Label.setText(w.getDate(2));
        day3Label.setText(w.getDate(3));
        day4Label.setText(w.getDate(4));
        day5Label.setText(w.getDate(5));
        day6Label.setText(w.getDate(6));
        day0icon = new Image("file:icons/" + w.getIcon(0));
        day0view.setImage(day0icon);
        day1icon = new Image("file:icons/" + w.getIcon(1));
        day1view.setImage(day1icon);
        day2icon = new Image("file:icons/" + w.getIcon(2));
        day2view.setImage(day2icon);
        day3icon = new Image("file:icons/" + w.getIcon(3));
        day3view.setImage(day3icon);
        day4icon = new Image("file:icons/" + w.getIcon(4));
        day4view.setImage(day4icon);
        day5icon = new Image("file:icons/" + w.getIcon(5));
        day5view.setImage(day5icon);
        day6icon = new Image("file:icons/" + w.getIcon(6));
        day6view.setImage(day6icon);
        day0RainChance.setText(w.getRainChance(0));
        day1RainChance.setText(w.getRainChance(1));
        day2RainChance.setText(w.getRainChance(2));
        day3RainChance.setText(w.getRainChance(3));
        day4RainChance.setText(w.getRainChance(4));
        day5RainChance.setText(w.getRainChance(5));
        day6RainChance.setText(w.getRainChance(6));
    }

    public void toggleToF(Weather w){
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            day0temp.setText(w.getAvgTempF(0));
            day1temp.setText(w.getAvgTempF(1));
            day2temp.setText(w.getAvgTempF(2));
            day3temp.setText(w.getAvgTempF(3));
            day4temp.setText(w.getAvgTempF(4));
            day5temp.setText(w.getAvgTempF(5));
            day6temp.setText(w.getAvgTempF(6));
            maxLabel.setText(w.getMaxF());
            minLabel.setText(w.getMinF());
            windLabel.setText(w.getWindSpeedMPH());
            visibilityLabel.setText(w.getVisibilityMI());
            elevationLabel.setText(w.getElevationFT());
            feelsLikeLabel.setText(w.getFeelsLikeF());
            precipLabel.setText(w.getPrecipIN());
        }
    }

    public void toggleToC(Weather w){
        if (!tempState) {
            tempLabel.setText(w.getTemperatureC());
            day0temp.setText(w.getAvgTempC(0));
            day1temp.setText(w.getAvgTempC(1));
            day2temp.setText(w.getAvgTempC(2));
            day3temp.setText(w.getAvgTempC(3));
            day4temp.setText(w.getAvgTempC(4));
            day5temp.setText(w.getAvgTempC(5));
            day6temp.setText(w.getAvgTempC(6));
            maxLabel.setText(w.getMaxC());
            minLabel.setText(w.getMinC());
            windLabel.setText(w.getWindSpeedKPH());
            visibilityLabel.setText(w.getVisibilityKM());
            elevationLabel.setText(w.getElevationM());
            feelsLikeLabel.setText(w.getFeelsLikeC());
            precipLabel.setText(w.getPrecipMM());
        }
    }

    public void toggleToHomePane(){
        if (!homeState){
            infoPane.setVisible(false);
            homePane.setVisible(true);
            Image image = new Image("file:bg/info_icon.png");
            infoIcon.setImage(image);
        }
    }

    public void toggleToInfoPane(){
        if (homeState) {
            homePane.setVisible(false);
            infoPane.setVisible(true);
            Image image = new Image("file:bg/home_icon.png");
            infoIcon.setImage(image);
        }
    }

    public void displayOnStart(){
        Weather w = new Weather("7");
        currentLocation = w.getCoordinates();
        displayHome(w);
        displayInfo(w);
        setDefaultImages();
        homePane.setVisible(true);
        infoPane.setVisible(false);
        setUpdatedTime(w);
    }

    public void displayInfo(Weather w){
        radarImageView.setImage(w.getRadarImage());
        if (tempState) {
            elevationLabel.setText(w.getElevationFT());
            feelsLikeLabel.setText(w.getFeelsLikeF());
            precipLabel.setText(w.getPrecipIN());
        }
        else{
            elevationLabel.setText(w.getElevationM());
            feelsLikeLabel.setText(w.getFeelsLikeC());
            precipLabel.setText(w.getPrecipMM());
        }
        pressureLabel.setText(w.getPressure());
        sunriseLabel.setText(w.getSunrise());
        sunsetLabel.setText(w.getSunset());
    }

    public void zoomIn(){
        int result = Integer.parseInt(zoomLevel);
        result++;
        zoomLevel = result + "";
        AsyncTask t = new zoomBackground();
        t.execute(zoomLevel);
    }

    public void zoomOut(){
        int result = Integer.parseInt(zoomLevel);
        result--;
        zoomLevel = result + "";
        AsyncTask t = new zoomBackground();
        t.execute(zoomLevel);
    }

    public void setUpdatedTime(Weather w){
        timeUpdated.setText(w.getCurrentTime());
    }

    public void setDialogPane(){
        if (dialogPane.isVisible()) {
            dialogPane.setVisible(false);
        }
    }

    public void setDefaultImages(){
        Image bg = new Image("file:bg/cloudy_1.png");
        Image arrow = new Image("file:bg/arrow_black1.png");
        Image info = new Image("file:bg/info_icon.png");
        infoIcon.setImage(info);
        arrowImage.setImage(arrow);
        bgImage.setImage(bg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsBox.setValue("");
        resultsBox.setItems(searchResults);
        tempState = true;
        homeState = true;
        zoomLevel = "8";
        displayOnStart();
    }
}

