package com.saeta.saetasigner;

import com.saeta.saetasigner.api.FirmadorService;
import com.saeta.saetasigner.controller.SplashController;

import com.saeta.saetasigner.utils.PropertyImp;
import com.saeta.saetasigner.utils.SsignerProperty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SplashView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SplashController splashController = fxmlLoader.getController();


        /*
        Parameters parameters = getParameters();
        if(parameters.getRaw().size() > 0)
            splashController.initializeWithParams();
        */

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(String.valueOf(HelloApplication.class.getResource("img/Icon.png"))));
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        try {
            SsignerProperty ssignerProperty = new SsignerProperty();

            String user = System.getProperty("user.name");
            //String ruta_directorio = "C:\\Users\\" + user + "\\SSFD_FT";
            String ruta_directorio = "C:\\origen";
            System.setProperty("miapp.logdir", ruta_directorio);

            logger.info("Iniciando la aplicación...");
            String[] url = args[0].split(Pattern.quote("//"));
            //ArrayList<String> argumentos = new ArrayList<>(Arrays.asList(url[1].split("%")));
            //String idClient = argumentos.get(0);

            String id = url[1];
            logger.info("ID CAPTURADO: "+id.substring(0,id.length() -1));
            PropertyImp propertyImp = new PropertyImp();
            propertyImp.updateProperty("id", id.substring(0,id.length() -1));
            System.setProperty("http.maxRedirects", "999");
            launch();
        }catch (Exception e){

        }
    }

}