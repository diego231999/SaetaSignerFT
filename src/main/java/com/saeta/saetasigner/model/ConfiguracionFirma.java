package com.saeta.saetasigner.model;

import java.util.List;

public class ConfiguracionFirma {
    private String x;
    private String y;
    private String ancho;
    private String alto;
    private String pagina;
    private List<String> nombrePdf;
    private List<String> nombreImg;
    private String dni;
    private String etiqueta;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public List<String> getNombrePdf() {
        return nombrePdf;
    }

    public void setNombrePdf(List<String> nombrePdf) {
        this.nombrePdf = nombrePdf;
    }

    public List<String> getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(List<String> nombreImg) {
        this.nombreImg = nombreImg;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
