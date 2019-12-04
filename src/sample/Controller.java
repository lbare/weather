package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;
import sierra.AsyncTask;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField zipField, locationField, weatherField, locationInput;

    @FXML
    ComboBox resultsBox;

    @FXML
    Label tempLabel, weatherLabel, locationLabel, day0temp, day1temp, day2temp, day3temp, day4temp, day5temp, day6temp, fLabel,
            day0Label, day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, currentDateLabel, minLabel, maxLabel;

    @FXML
    ImageView imageView, day0view, day1view, day2view, day3view, day4view, day5view, day6view, bgImage;

    // keeps track of whether the Go or myLocation was pressed for the RadioButtons
    private int buttonClicked;
    private boolean tempState;
    private HashMap<String,String> map;
    private String selection, locationName;
    private Image Icon1, day0icon, day1icon, day2icon, day3icon, day4icon, day5icon, day6icon;;

    ObservableList<String> searchResults = FXCollections.observableArrayList("");

    public void handleGoButton(ActionEvent e){
        selection = map.get(resultsBox.getValue()); // gets selected value from ComboBox
        Weather w = new Weather(selection); // creates new Weather object using the selected location as the parameter
        buttonClicked = 0;
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            minLabel.setText(w.getMinF());
            maxLabel.setText(w.getMaxF());
            fLabel.setText("°F");
        }
        else {
            tempLabel.setText(w.getTemperatureC());
            minLabel.setText(w.getMinC());
            maxLabel.setText(w.getMaxC());
            fLabel.setText("°C");
        }
        weatherLabel.setText(w.getWeather());
        String currentLocation = resultsBox.getValue().toString();
        String temp = currentLocation.substring(currentLocation.indexOf(",") + 1);
        currentLocation = currentLocation.replace(temp.substring(temp.indexOf(",")), "");
        locationLabel.setText(currentLocation);
        setVisible();
        forecastLabels();

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

    public void myLocationButtonHandler(ActionEvent e){
        Weather w = new Weather();
        buttonClicked = 1;
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            minLabel.setText(w.getMinF());
            maxLabel.setText(w.getMaxF());
            fLabel.setText("°F");
        }
        else {
            tempLabel.setText(w.getTemperatureC());
            minLabel.setText(w.getMinC());
            maxLabel.setText(w.getMaxC());
            fLabel.setText("°C");
        }
        weatherLabel.setText(w.getWeather());
        locationLabel.setText(w.getCityState());
        setVisible();
        forecastLabels();

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

    public void myLocationButtonHandler(){
        Weather w = new Weather();
        buttonClicked = 1;
        if (tempState) {
            tempLabel.setText(w.getTemperatureF());
            minLabel.setText(w.getMinF());
            maxLabel.setText(w.getMaxF());
            fLabel.setText("°F");
        }
        else {
            tempLabel.setText(w.getTemperatureC());
            minLabel.setText(w.getMinC());
            maxLabel.setText(w.getMaxC());
            fLabel.setText("°C");
        }
        weatherLabel.setText(w.getWeather());
        locationLabel.setText(w.getCityState());
        setVisible();
        forecastLabels();

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

    public void autoFillUpdate(KeyEvent e){
        if (locationInput.getText().length() > 2) {
            AutoFill a = new AutoFill(locationInput.getText());
            map = new HashMap<String, String>();
            map = a.getMap();
            searchResults = FXCollections.observableArrayList(a.getLocationResultsArray());
            resultsBox.setItems(searchResults);
            resultsBox.show();
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

    public void toggleButton(){
        if (buttonClicked == 0) {
            selection = map.get(resultsBox.getValue());
            Weather w = new Weather(selection);
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
                fLabel.setText("°F");
            }
            else {
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
                fLabel.setText("°C");
            }
        }
        else if (buttonClicked == 1) {
            Weather w = new Weather();
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
                fLabel.setText("°F");
            }
            else {
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
                fLabel.setText("°C");
            }
        }
        tempState = !tempState;
    }

    public void clearFields(ActionEvent e){
        locationInput.clear();
        resultsBox.setItems(searchResults);
    }

    public void forecastLabels(){
        if (buttonClicked == 0) {
            selection = map.get(resultsBox.getValue());
            Weather w = new Weather(selection);
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
        else {
            Weather w = new Weather();
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
            currentDateLabel.setText(w.getDate(0));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsBox.setValue("");
        resultsBox.setItems(searchResults);
        tempState = true;
        Image bg = new Image("file:bg/rainy_blur.png");
        bgImage.setImage(bg);
        myLocationButtonHandler();
    }
}

