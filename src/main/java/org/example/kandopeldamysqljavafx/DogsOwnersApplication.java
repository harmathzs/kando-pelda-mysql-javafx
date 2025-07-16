package org.example.kandopeldamysqljavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DogsOwnersApplication extends Application {
    public static boolean isRunningTest = false;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = null;
        if (!isRunningTest)  fxmlLoader = new FXMLLoader(DogsOwnersApplication.class.getResource("dogsowners-view.fxml"));
        Scene scene = null;
        if (!isRunningTest) scene = new Scene(fxmlLoader.load(), 1024, 768);
        if (!isRunningTest) stage.setTitle("Dogs and Owners!");
        if (!isRunningTest) stage.setScene(scene);
        if (!isRunningTest) stage.show();
        if (isRunningTest) Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}