package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("");
        Scene scene = new Scene(root, 400, 675);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        Rectangle rect = new Rectangle(400,675);
        rect.setArcHeight(20.0);
        rect.setArcWidth(20.0);
        root.setClip(rect);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
