package com.saeta.saetasigner.model;

import java.util.ArrayList;
import java.util.List;

public class GrupoFirmadoEntity {
    private Integer id_gru_proc_firma;
    byte[] bytesPdf;
    private String fileSignedName;

    public GrupoFirmadoEntity(Integer id_gru_proc_firma, byte[] bytesPdf ) {
        this.id_gru_proc_firma = id_gru_proc_firma;
        this.bytesPdf = bytesPdf;
    }

    public Integer getId_gru_proc_firma() {
        return id_gru_proc_firma;
    }

    public void setId_gru_proc_firma(Integer id_gru_proc_firma) {
        this.id_gru_proc_firma = id_gru_proc_firma;
    }

    public byte[] getBytesPdf() {
        return bytesPdf;
    }

    public void setBytesPdf(byte[] bytesPdf) {
        this.bytesPdf = bytesPdf;
    }

    public String getFileSignedName() {
        return fileSignedName;
    }

    public void setFileSignedName(String fileSignedName) {
        this.fileSignedName = fileSignedName;
    }
}
