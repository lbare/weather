package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import sierra.AsyncTask;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField locationInput;

    @FXML
    DialogPane dialogPane;

    @FXML
    ComboBox resultsBox;

    @FXML
    Label tempLabel, weatherLabel, locationLabel, day0temp, day1temp, day2temp, day3temp, day4temp, day5temp, day6temp, humidityLabel,
            day0Label, day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, currentDateLabel, minLabel, maxLabel, windLabel,
            visibilityLabel, locationErrorLabel;

    @FXML
    ImageView imageView, day0view, day1view, day2view, day3view, day4view, day5view, day6view, bgImage, arrowImage, tempImage, windDirection,
               radarImageView;

    // keeps track of whether the Go or myLocation was pressed for the RadioButtons
    private int buttonClicked;
    private boolean tempState;
    private HashMap<String,String> map;
    private String selection, zoomLevel;
    private Image Icon1, day0icon, day1icon, day2icon, day3icon, day4icon, day5icon, day6icon;
    private Weather w1;
    private ObservableList<String> searchResults = FXCollections.observableArrayList("");

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

    public void clearSearch() {
        locationInput.clear();
    }

    public void checkComboBox(ActionEvent e){
        if (resultsBox.getValue() != null){
            handleGoButton();
            clearSearch();
        }
    }

    public void setVisible(){
        locationLabel.setVisible(true);
        weatherLabel.setVisible(true);
        tempLabel.setVisible(true);
        weatherLabel.setVisible(true);
        locationLabel.setVisible(true);
        imageView.setVisible(true);
    }

    public class locationSearchBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query){
            selection = map.get(resultsBox.getValue()); // gets selected value from ComboBox
            Weather w = new Weather(selection,"d"); // creates new Weather object using the selected location as the parameter
            return w;
        }
        public void onPostExecute(Weather w){
            try {
                displayInfo(w);
            }
            catch (IllegalStateException ie) {
                dialogPane.setVisible(true);
                locationErrorLabel.setText(resultsBox.getValue().toString());
            }
        }
    }

    public class myLocationBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query){
            Weather w = new Weather("d"); // creates new Weather object using the selected location as the parameter
            return w;
        }
        public void onPostExecute(Weather w){
            displayInfo(w);
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

    public class toggleBackground extends AsyncTask<String, Weather>{
        public Weather doInBackground(String query){
            if (buttonClicked == 0) {
                w1 = new Weather(zoomLevel);
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

    public void displayRadarImage(Weather w){
        radarImageView.setImage(w.getRadarImage());
    }

    public void displayInfo(Weather w) {
        tempState = true;
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            minLabel.setText(w.getMinF());
            maxLabel.setText(w.getMaxF());
            windLabel.setText(w.getWindSpeedMPH());
            visibilityLabel.setText(w.getVisibilityMI());
            tempImage.setImage(new Image("file:icons/fah.png"));
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
        setVisible();
        forecastLabels(w);

        Icon1 = new Image("file:icons/" + w.getIcon());
        imageView.setImage(Icon1);

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
        Icon1 = new Image("file:images/" + w.getIcon());
        imageView.setImage(Icon1);
        currentDateLabel.setText(w.getDate(0));
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
            tempImage.setImage(new Image("file:icons/fah.png"));
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
            tempImage.setImage(new Image("file:icons/cel.png"));
        }
    }

    public void displayOnStart(){
        Weather w = new Weather(zoomLevel);
        displayInfo(w);
        displayRadarImage(w);
    }

    public void setDialogPane(){
        if (dialogPane.isVisible()) {
            dialogPane.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsBox.setValue("");
        resultsBox.setItems(searchResults);
        tempState = true;
        Image bg = new Image("file:bg/day.png");
        Image arrow = new Image("file:bg/arrow_black1.png");
        Image temp = new Image("file:icons/fah.png");
        zoomLevel = "7";
        tempImage.setImage(temp);
        arrowImage.setImage(arrow);
        bgImage.setImage(bg);
        displayOnStart();
    }
}

