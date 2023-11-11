package com.saeta.saetasigner.service;

import com.saeta.saetasigner.utils.Certificate;
import com.saeta.saetasigner.utils.CertificateImp;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class CertificadoService {
    private final CertificateImp certificateImp;

    public CertificadoService() {
        this.certificateImp = new CertificateImp();
    }

    public ArrayList<String> getCertificates(String dni, String etiqueta) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        List<Certificate> listCertificates = certificateImp.getCertificates();
        String var="";
        if(etiqueta.contains("J")){
            var = "-J";
        }else{
            var = "-N";
        }
        if(listCertificates != null) {
            ArrayList<String> certificateNames = new ArrayList<>();
            for (int i = 0; i < listCertificates.size(); i++) {
                if (listCertificates.get(i).getNumero_documento().equalsIgnoreCase(dni) && listCertificates.get(i).getNombre_listar().contains(var)) {
                    certificateNames.add(listCertificates.get(i).getNombre_listar());
                }
            }
            return certificateNames;
        }
        return null;
    }
}
