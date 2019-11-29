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
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField zipField, locationField, weatherField, locationInput;

    @FXML
    ComboBox resultsBox;

    @FXML
    RadioButton fToggle, cToggle;

    @FXML
    Label tempLabel, weatherLabel, locationLabel, day0temp, day1temp, day2temp, day3temp, day4temp, day5temp, day6temp,
            day0Label, day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, currentDateLabel;

    @FXML
    ToggleGroup tempToggle;

    @FXML
    ImageView imageView, day0view, day1view, day2view, day3view, day4view, day5view, day6view;

    // keeps track of whether the Go or myLocation was pressed for the RadioButtons
    private int buttonClicked;
    private boolean tempState;
    private HashMap<String,String> map;
    private String selection;
    private Image Icon1, day0icon, day1icon, day2icon, day3icon, day4icon, day5icon, day6icon;;

    ObservableList<String> searchResults = FXCollections.observableArrayList("");



    public void goButtonHandler(ActionEvent e){
        if ((zipField.getText().matches("[0-9]+") && zipField.getText().length() == 5)) {
            Double num = Double.parseDouble(zipField.getText());
            Weather w = new Weather(zipField.getText());
            tempLabel.setText(w.getTemperatureF());
            weatherField.setText(w.getWeather());
            locationField.setText(w.getCityState());
            setVisible();
            tempToggle.selectToggle(fToggle);
            buttonClicked = 0;
            tempState = true;

            Image Icon1 = new Image("file:images/" + w.getIcon());
            imageView.setImage(Icon1);
        }
    }

    public void myLocationButtonHandler(ActionEvent e){
        Weather w = new Weather();
        tempLabel.setText(w.getTemperatureF());
        weatherLabel.setText(w.getWeather());
        locationLabel.setText(w.getCityState());
        setVisible();
        forecastLabels();
        tempToggle.selectToggle(fToggle);
        buttonClicked = 1;
        tempState = true;

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

    public void setVisible(){
        fToggle.setVisible(true);
        cToggle.setVisible(true);
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
                tempLabel.setText(w.getTemperatureC());
                day0temp.setText(w.getAvgTempC(0));
                day1temp.setText(w.getAvgTempC(1));
                day2temp.setText(w.getAvgTempC(2));
                day3temp.setText(w.getAvgTempC(3));
                day4temp.setText(w.getAvgTempC(4));
                day5temp.setText(w.getAvgTempC(5));
                day6temp.setText(w.getAvgTempC(6));
                tempState = false;
            }
            else {
                tempLabel.setText(w.getTemperatureF());
                day0temp.setText(w.getAvgTempF(0));
                day1temp.setText(w.getAvgTempF(1));
                day2temp.setText(w.getAvgTempF(2));
                day3temp.setText(w.getAvgTempF(3));
                day4temp.setText(w.getAvgTempF(4));
                day5temp.setText(w.getAvgTempF(5));
                day6temp.setText(w.getAvgTempF(6));
                tempState = true;
            }
        }
        else if (buttonClicked == 1) {
            Weather w = new Weather();
            if (tempState) {
                tempLabel.setText(w.getTemperatureC());
                day0temp.setText(w.getAvgTempC(0));
                day1temp.setText(w.getAvgTempC(1));
                day2temp.setText(w.getAvgTempC(2));
                day3temp.setText(w.getAvgTempC(3));
                day4temp.setText(w.getAvgTempC(4));
                day5temp.setText(w.getAvgTempC(5));
                day6temp.setText(w.getAvgTempC(6));
                tempState = false;
            }
            else {
                tempLabel.setText(w.getTemperatureF());
                day0temp.setText(w.getAvgTempF(0));
                day1temp.setText(w.getAvgTempF(1));
                day2temp.setText(w.getAvgTempF(2));
                day3temp.setText(w.getAvgTempF(3));
                day4temp.setText(w.getAvgTempF(4));
                day5temp.setText(w.getAvgTempF(5));
                day6temp.setText(w.getAvgTempF(6));
                tempState = true;
            }
        }
    }

    public void autoFillUpdate(ActionEvent e){
        AutoFill a = new AutoFill(locationInput.getText());
        map = new HashMap<String,String>();
        map = a.getMap();
        searchResults = FXCollections.observableArrayList(a.getLocationResults());
        resultsBox.setItems(searchResults);
    }

    public void clearFields(ActionEvent e){
        locationInput.clear();
        resultsBox.setItems(searchResults);
    }

    public void handleGoButton(ActionEvent e){
        selection = map.get(resultsBox.getValue());
        Weather w = new Weather(selection);

        tempLabel.setText(w.getTemperatureF());
        weatherLabel.setText(w.getWeather());
        locationLabel.setText(w.getCityState());
        setVisible();
        tempToggle.selectToggle(fToggle);
        buttonClicked = 0;
        tempState = true;
        forecastLabels(1);
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

    public void forecastLabels(){
        Weather w = new Weather();
        day0temp.setText(w.getAvgTempF(0));
        day1temp.setText(w.getAvgTempF(1));
        day2temp.setText(w.getAvgTempF(2));
        day3temp.setText(w.getAvgTempF(3));
        day4temp.setText(w.getAvgTempF(4));
        day5temp.setText(w.getAvgTempF(5));
        day6temp.setText(w.getAvgTempF(6));
        day0Label.setText(w.getDate(0));
        day1Label.setText(w.getDate(1));
        day2Label.setText(w.getDate(2));
        day3Label.setText(w.getDate(3));
        day4Label.setText(w.getDate(4));
        day5Label.setText(w.getDate(5));
        day6Label.setText(w.getDate(6));
        currentDateLabel.setText(w.getDate(0));
    }

    public void forecastLabels(int num){
        selection = map.get(resultsBox.getValue());
        Weather w = new Weather(selection);
        day0temp.setText(w.getAvgTempF(0));
        day1temp.setText(w.getAvgTempF(1));
        day2temp.setText(w.getAvgTempF(2));
        day3temp.setText(w.getAvgTempF(3));
        day4temp.setText(w.getAvgTempF(4));
        day5temp.setText(w.getAvgTempF(5));
        day6temp.setText(w.getAvgTempF(6));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsBox.setValue("");
        resultsBox.setItems(searchResults);
    }
}

