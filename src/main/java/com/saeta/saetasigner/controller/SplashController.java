package com.saeta.saetasigner.controller;

import com.saeta.saetasigner.HelloApplication;
import com.saeta.saetasigner.api.FirmadorService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    public SplashController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SplashScreen().start();
    }

    class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firmador.fxml"));
                    Stage stage = new Stage();
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    FirmaController firmaIndividualController = fxmlLoader.getController();
                    FirmadorService restService = new FirmadorService();
                    try {
                        String msgRsponse = restService.downloadServiceFiles();

                        firmaIndividualController.initializeWithParams();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.getIcons().add(new Image(String.valueOf(HelloApplication.class.getResource("img/Icon.png"))));
                    scene.setFill(Color.TRANSPARENT);
                    stage.show();
                    rootPane.getScene().getWindow().hide();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
