package com.saeta.saetasigner.utils;

public class Certificate {
    private String alias;
    private String nombre;
    private String nombre_listar;

    private String cargo;
    private String empresa;
    private String fecha_emision;
    private String fecha_caducidad;
    private String numero_documento;
    private String estado;

    public Certificate(String alias, String nombre, String nombre_listar, String cargo, String empresa, String fecha_emision, String fecha_caducidad, String numero_documento, String estado) {
        this.alias = alias;
        this.nombre = nombre;
        this.nombre_listar = nombre_listar;
        this.cargo = cargo;
        this.empresa = empresa;
        this.fecha_emision = fecha_emision;
        this.fecha_caducidad = fecha_caducidad;
        this.numero_documento = numero_documento;
        this.estado = estado;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(String fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_listar() {
        return nombre_listar;
    }

    public void setNombre_listar(String nombre_listar) {
        this.nombre_listar = nombre_listar;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
