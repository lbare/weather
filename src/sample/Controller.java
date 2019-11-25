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
    Label tempLabel, weatherLabel, locationLabel, day0, day1, day2, day3, day4, day5, day6;

    @FXML
    ToggleGroup tempToggle;

    @FXML
    ImageView imageView;

    // keeps track of whether the Go or myLocation was pressed for the RadioButtons
    private int buttonClicked;
    private HashMap<String,String> map;
    private String selection;

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

            Image Icon1 = new Image("file:images/" + w.getIcon());
            imageView.setImage(Icon1);
        }
    }

    public void myLocationButtonHandler(ActionEvent e){
        Weather w = new Weather(  );
        tempLabel.setText(w.getTemperatureF());
        weatherField.setText(w.getWeather());
        locationField.setText(w.getCityState());
        setVisible();
        tempToggle.selectToggle(fToggle);
        buttonClicked = 1;
        zipField.clear();

        Image Icon1 = new Image("file:icons/" + w.getIcon());
        imageView.setImage(Icon1);
    }

    public void setVisible(){
        fToggle.setVisible(true);
        cToggle.setVisible(true);
        locationField.setVisible(true);
        weatherField.setVisible(true);
        tempLabel.setVisible(true);
        weatherLabel.setVisible(true);
        locationLabel.setVisible(true);
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
        weatherField.setText(w.getWeather());
        locationField.setText(w.getCityState());
        setVisible();
        tempToggle.selectToggle(fToggle);
        buttonClicked = 0;

        Image Icon1 = new Image("file:images/" + w.getIcon());
        imageView.setImage(Icon1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsBox.setValue("");
        resultsBox.setItems(searchResults);
    }
}

