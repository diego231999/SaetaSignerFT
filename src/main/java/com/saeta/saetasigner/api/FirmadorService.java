package com.saeta.saetasigner.api;

import java.io.IOException;





import com.saeta.saetasigner.model.ConfigResponse;
import com.saeta.saetasigner.model.GrupoFirmadoEntity;
import com.saeta.saetasigner.model.ResponseProcesoFirmaEntity;
import com.saeta.saetasigner.utils.FileImp;

import com.saeta.saetasigner.utils.PropertyImp;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmadorService {
    private final FileImp fileImp;
    private final String urlServer;
    private final String id;
    private static final Logger logger = LoggerFactory.getLogger(FirmadorService.class);

    public FirmadorService() {

        PropertyImp propertyImp = new PropertyImp();
        this.urlServer = propertyImp.getProperty("urlServer");
        this.id = propertyImp.getProperty("id");
        this.fileImp = new FileImp();
    }


    public String downloadServiceFiles() throws IOException {


        RestTemplate restTemplate = new RestTemplate();
        String url = this.urlServer + "v1/documento/configuracion-firma?id_gpf=" + id;
        Gson gson = new Gson();

        logger.info("URL SERVICIO DESCARGA: : " + url);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(url, String.class);
            logger.info("RESPUESTA SERVICIO DESCARGA: : " + response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("RESPUESTA SERVICIO DESCARGA: : " + e.getMessage());
        }

        assert response != null;
        if (!response.getStatusCode().is2xxSuccessful()) {
            return "Error conectando al servidor: " + response.getBody();
            //return "Error conectando al servidor";
        }

        ResponseProcesoFirmaEntity responseWSEntity = gson.fromJson(response.getBody(), ResponseProcesoFirmaEntity.class);
        if (responseWSEntity.getMessage().equals("Operación exitosa!")) {
            ConfigResponse configFirma = (ConfigResponse) responseWSEntity.getConfig();

            fileImp.saveFileB64(configFirma.getBytesPdf(), configFirma.getNombrePdf(), 1);
            //fileImp.saveFile(grupoProcesoFirma.getBytesConf(), grupoProcesoFirma.getNameConf(), 3);
            //PropertyService propertyService = new PropertyService(this.ssignerProperty);
            //propertyService.copyPropertiesFromUserProperty(ssignerProperty.configurationFolderPath + "/" + grupoProcesoFirma.getNameConf());
            fileImp.saveFileB64(configFirma.getBytesImagen(), "firma.jpg", 3);
            PropertyImp propertyImp = new PropertyImp();
            propertyImp.updateProperty("dni", configFirma.getDni());
            //propertyImp.updateProperty("dni", "09950333");
            propertyImp.updateProperty("x", configFirma.getX());
            propertyImp.updateProperty("y", configFirma.getY());
            propertyImp.updateProperty("ancho", configFirma.getAncho());
            propertyImp.updateProperty("alto", configFirma.getAlto());
            propertyImp.updateProperty("pagina", configFirma.getPagina());
            propertyImp.updateProperty("nombrePdf", configFirma.getNombrePdf());
            propertyImp.updateProperty("etiqueta", configFirma.getEtiqueta());


        } else {
            return responseWSEntity.getMessage();
        }

        /*
        PropertyImp propertyImp = new PropertyImp();
        propertyImp.updateProperty("dni", "09950333" );
        propertyImp.updateProperty("x", "100");
        propertyImp.updateProperty("y", "100");
        propertyImp.updateProperty("ancho", "100");
        propertyImp.updateProperty("alto", "100");
        propertyImp.updateProperty("pagina", "1");
        propertyImp.updateProperty("nombrePdf", "prueba.pdf");
         */
        return "ok";
    }

    public void updateGrupoProcesoFirma() throws IOException {

        PropertyImp propertyImp = new PropertyImp();
        String nombrePdf = propertyImp.getProperty("nombrePdf");
        String id = propertyImp.getProperty("id");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = this.urlServer + "v1/documento/uploadGrupoFirmado";
        logger.info("URL SERVICIO CARGA: : " + url);
        String fileName = "";
        int i = nombrePdf.lastIndexOf(".");
        fileName = nombrePdf.substring(0, i);
        fileName += "[F]";
        byte[] fileSignedBytes = null;
        String fileSignedName = fileName + ".pdf";

        fileSignedBytes = (this.fileImp.loadFile(fileSignedName, 2));

        GrupoFirmadoEntity grupoFirmado = new GrupoFirmadoEntity(
                Integer.parseInt(id),
                fileSignedBytes
        );
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        HttpEntity<GrupoFirmadoEntity> requestEntity = new HttpEntity<>(grupoFirmado, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        logger.info("RESPUESTA SERVICIO CARGA: : " + response.getBody());
    }
}
