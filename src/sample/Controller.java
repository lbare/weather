package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    TextField zipField, weatherField, locationField;

    @FXML
    RadioButton fToggle, cToggle;

    @FXML
    Label tempLabel, weatherLabel, locationLabel, weather1Label, location1Label, day0, day1, day2, day3, day4, day5, day6;

    @FXML
    ToggleGroup tempToggle;

    @FXML
    ImageView imageView;

    // keeps track of whether the Go or myLocation was pressed for the RadioButtons
    private int buttonClicked;

    public void goButtonHandler(ActionEvent e){
        if ((zipField.getText().matches("[0-9]+") && zipField.getText().length() == 5)) {
            Double num = Double.parseDouble(zipField.getText());
            Weather w = new Weather(zipField.getText());
            tempLabel.setText(w.getTemperatureF());
            weatherField.setText(w.getWeather());
            weather1Label.setText(w.getWeather());
            locationField.setText(w.getCityState());
            location1Label.setText(w.getCityState());
            setVisible();
            tempToggle.selectToggle(fToggle);
            buttonClicked = 0;

            Image Icon1 = new Image("file:images/" + w.getIcon());
            imageView.setImage(Icon1);
        }
    }

    public void myLocationButtonHandler(ActionEvent e){
        Weather w = new Weather();
        tempLabel.setText(w.getTemperatureF());
        weatherField.setText(w.getWeather());
        weather1Label.setText(w.getWeather());
        locationField.setText(w.getCityState());
        location1Label.setText(w.getCityState());
        setVisible();
        tempToggle.selectToggle(fToggle);
        buttonClicked = 1;
        zipField.clear();

        Image Icon1 = new Image("file:images/" + w.getIcon());
        imageView.setImage(Icon1);
    }

    public void setVisible(){
        fToggle.setVisible(true);
        cToggle.setVisible(true);
        locationField.setVisible(true);
        weatherField.setVisible(true);
        tempLabel.setVisible(true);
        weatherLabel.setVisible(true);
        weather1Label.setVisible(true);
        locationLabel.setVisible(true);
        location1Label.setVisible(true);
        imageView.setVisible(true);
    }

    public void toggleButton(){
        if (buttonClicked == 0) {
            Weather w = new Weather(zipField.getText());
            if (tempToggle.getSelectedToggle().equals(cToggle)) {
                tempLabel.setText(w.getTemperatureC());
            }
            else if (tempToggle.getSelectedToggle().equals(fToggle)) {
                tempLabel.setText(w.getTemperatureF());
            }
        }
        else if (buttonClicked == 1) {
            Weather w = new Weather();
            if (tempToggle.getSelectedToggle().equals(cToggle)) {
                tempLabel.setText(w.getTemperatureC());
            }
            else if (tempToggle.getSelectedToggle().equals(fToggle)) {
                tempLabel.setText(w.getTemperatureF());
            }
        }
    }

}

