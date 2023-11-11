package com.saeta.saetasigner.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SsignerProperty {

    public String id;
    public String carpetaPorFirmar;
    public String carpetaFirmados;
    public String carpetaImagenes;

    public SsignerProperty() throws IOException {
        String user = System.getProperty("user.name");
        String ruta_directorio = "C:\\Users\\" + user + "\\SSFD_FT";
        File file = new File(ruta_directorio + "\\config.properties");
        if (file.isFile()) {
            //System.out.println("Existe ruta");
        } else {
            File file2 = new File(ruta_directorio);
            file2.mkdir();
            Properties properties = new Properties();
            FileOutputStream out = new FileOutputStream(ruta_directorio + "\\config.properties");
            properties.setProperty("urlServer", "https://ftcapital-ws.plussigner.com:9082/");
            properties.setProperty("id", "0");
            properties.setProperty("carpetaPorFirmar", "C:/SSFD_FT/porfirmar");
            properties.setProperty("carpetaFirmados", "C:/SSFD_FT/firmados");
            properties.setProperty("carpetaImagenes", "C:/SSFD_FT/imagenes");
            properties.setProperty("dni", "73005544");
            properties.setProperty("x", "0");
            properties.setProperty("y", "0");
            properties.setProperty("ancho", "0");
            properties.setProperty("alto", "0");
            properties.setProperty("pagina", "0");
            properties.setProperty("nombrePdf", "nombre.pdf");
            properties.setProperty("nombreImg", "firma.jpg");
            properties.setProperty("etiqueta", "");

            properties.store(out, null);
            out.close();
        }
    }
}
