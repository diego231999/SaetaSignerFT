package com.saeta.saetasigner.model;

public class ConfigResponse {
    private String x;
    private String y;
    private String ancho;
    private String alto;
    private String pagina;
    private String nombrePdf;
    private String bytesPdf;
    private String bytesImagen;
    private String dni;
    private boolean firmaVisible;
    private boolean signAllPages;
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

    public String getNombrePdf() {
        return nombrePdf;
    }

    public void setNombrePdf(String nombrePdf) {
        this.nombrePdf = nombrePdf;
    }

    public String getBytesPdf() {
        return bytesPdf;
    }

    public void setBytesPdf(String bytesPdf) {
        this.bytesPdf = bytesPdf;
    }

    public String getBytesImagen() {
        return bytesImagen;
    }

    public void setBytesImagen(String bytesImagen) {
        this.bytesImagen = bytesImagen;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isFirmaVisible() {
        return firmaVisible;
    }

    public void setFirmaVisible(boolean firmaVisible) {
        this.firmaVisible = firmaVisible;
    }

    public boolean isSignAllPages() {
        return signAllPages;
    }

    public void setSignAllPages(boolean signAllPages) {
        this.signAllPages = signAllPages;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
