package com.saeta.saetasigner.controller;

import com.saeta.saetasigner.api.FirmadorService;
import com.saeta.saetasigner.model.ConfiguracionFirma;
import com.saeta.saetasigner.service.CertificadoService;
import com.saeta.saetasigner.service.SignService;
import com.saeta.saetasigner.utils.FileImp;
import com.saeta.saetasigner.utils.PropertyImp;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class FirmaController {

    @FXML
    private AnchorPane mainWindow;
    @FXML
    AnchorPane plataformaPane;
    @FXML
    private ComboBox<String> certificadoPlataformaComboBox;
    @FXML
    private ImageView imagenRefresh;
    @FXML
    private ImageView archivoLoader3;
    @FXML
    private Button firmarBtn1;
    @FXML
    private Text errorAlertMessage1;
    @FXML
    private Text successAlertMessage1;
    @FXML
    private Label textoPlataforma;
    @FXML
    private StackPane succesAlert1;
    @FXML
    private StackPane errorAlert1;
    @FXML
    private Button cancelarButton;
    double xOffset, yOffset;
    private boolean showingAlert = false;
    private final FirmadorService restService = new FirmadorService();
    private final CertificadoService certificadoService = new CertificadoService();
    private final ConfiguracionFirma cf = new ConfiguracionFirma();
    private final SignService signService = new SignService();

    private final FileImp fileImp = new FileImp();


    @FXML
    void onMouseDraged(MouseEvent event) {
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setX(event.getScreenX() + this.xOffset);
        stage.setY(event.getScreenY() + this.yOffset);
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        this.xOffset = stage.getX() - event.getScreenX();
        this.yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    private void onMinimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose2(MouseEvent event) throws IOException {
        //this.fileService.cleanFolders();
        /*
        if (firmaPorPlataforma) {
            restService.updateGrupoProcesoFirma();
        }
         */
        fileImp.cleanFolder(1);
        fileImp.cleanFolder(3);
        new Timer().schedule(new TimerTask() {
            public void run() {
                Platform.exit();
                System.exit(0);
            }
        }, 50);

    }

    @FXML
    void onCertificadoPlataformaClicked(MouseEvent event) throws Exception {
        PropertyImp propertyImp = new PropertyImp();
        String dni = propertyImp.getProperty("dni");
        String etiqueta = propertyImp.getProperty("etiqueta");
        this.listCertificates2(dni,etiqueta);
        //certificadoPlataformaComboBox.getSelectionModel().selectFirst();
    }

    private void listCertificates2(String dni, String etiqueta) throws Exception {
        ArrayList<String> certificateNames = this.certificadoService.getCertificates(dni,etiqueta);
        if (certificateNames != null) {
            if (this.certificadoPlataformaComboBox.getItems().size() > 0)
                this.certificadoPlataformaComboBox.getItems().clear();
            this.certificadoPlataformaComboBox.getItems().addAll(certificateNames);
            this.certificadoPlataformaComboBox.setValue(certificateNames.get(0));
        }
    }

    public void initializeWithParams() throws Exception {

        PropertyImp propertyImp = new PropertyImp();
        String x = propertyImp.getProperty("x");
        String y = propertyImp.getProperty("y");
        String alto = propertyImp.getProperty("alto");
        String ancho = propertyImp.getProperty("ancho");
        String pagina = propertyImp.getProperty("pagina");
        String nombrePdf = propertyImp.getProperty("nombrePdf");
        String nombreImg = propertyImp.getProperty("nombreImg");
        String dni = propertyImp.getProperty("dni");
        String etiqueta = propertyImp.getProperty("etiqueta");


        List<String> nombres_pdf = new ArrayList<>();
        nombres_pdf.add(nombrePdf);
        List<String> nombres_img = new ArrayList<>();
        nombres_img.add(nombreImg);
        cf.setX(x);
        cf.setY(y);
        cf.setAlto(alto);
        cf.setAncho(ancho);
        cf.setPagina(pagina);
        cf.setNombreImg(nombres_img);
        cf.setNombrePdf(nombres_pdf);
        cf.setDni(dni);
        cf.setEtiqueta(etiqueta);

        this.listCertificates2(cf.getDni(),cf.getEtiqueta());
        //this.listCertificates2("42088545","[42088545-J]");

        //certificadoPlataformaComboBox.getSelectionModel().selectFirst();
        this.plataformaPane.setVisible(true);

    }

    @FXML
    void onSignPdf() throws Exception {

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    archivoLoader3.setVisible(true);
                    cancelarButton.setDisable(true);
                    firmarBtn1.setDisable(true);
                    signService.signFile(cf, certificadoPlataformaComboBox.getValue());

                } catch (Exception e) {
                    showAlert2(2, "Error en los parámetros de firma");
                }

                restService.updateGrupoProcesoFirma();

                //succesAlert1.setVisible(true);
                //showAlert2(1, "Firmado exitosamente");


                archivoLoader3.setVisible(false);

                showAlert2(1, "Firmado exitosamente");

                Thread.sleep(3000);

                //-------------------------------CAMBIOO-----------------------------//

                showAlert2(1, "....Cerrando aplicación....");
                Thread.sleep(1000);
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        Platform.exit();
                        System.exit(0);
                    }
                }, 1000);

                fileImp.cleanFolder(1);
                fileImp.cleanFolder(3);

                return null;
            }
        };
        (new Thread(task)).start();
    }

    private void showAlert2(Integer tipoAlerta, String mensaje) throws InterruptedException {
        if (!this.showingAlert) {
            TranslateTransition translate = new TranslateTransition();
            this.showingAlert = true;
            new Thread(() -> {
                if (tipoAlerta == 1) {
                    this.successAlertMessage1.setText(mensaje);
                    this.succesAlert1.setVisible(true);
                    translate.setNode(this.succesAlert1);
                    translate.setByX(-210);
                    translate.play();
                } else {
                    this.errorAlertMessage1.setText(mensaje);
                    translate.setNode(this.errorAlert1);
                    translate.setByX(-210);
                    translate.play();
                }
                try {
                    Thread.sleep(4000);
                    if (tipoAlerta == 1) {
                        this.succesAlert1.setVisible(false);
                    } else {
                        this.errorAlert1.setVisible(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        translate.setByX(210);
                        translate.play();
                        showingAlert = false;
                    }
                });
            }).start();
        }
    }


}
