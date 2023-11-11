package com.saeta.saetasigner.utils;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CertificateImp {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");

    public List<Certificate> getCertificates() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        String alias, dni, cargo,empresa, nombreListar, certUserName, serialNumber, OS, creationDate, expirationDate, valid;
        List<Certificate> listCertificates = new ArrayList<>();

        OS = System.getProperty("os.name", "generic").toLowerCase();

        if (OS.contains("win")) {

            KeyStore ks = KeyStore.getInstance("Windows-MY");
            ks.load(null);
            Enumeration listKsCerts = ks.aliases();

            while (listKsCerts.hasMoreElements()) {
                try {
                    alias = (String) listKsCerts.nextElement();
                    X509Certificate x509Certificate = (X509Certificate) ks.getCertificate(alias);
                    certUserName = this.getCertUserName(x509Certificate);
                    serialNumber = this.getSerialNumber(x509Certificate);
                    cargo = this.getCargo(x509Certificate);
                    empresa = this.getEmpresa(x509Certificate);
                    X500Principal subjectPrincipal = x509Certificate.getSubjectX500Principal();
                    String ruc = getOrganizationUnitFromCertificate(x509Certificate);

                    if (serialNumber.contains("DNI")) {
                        String[] parts = serialNumber.split(":");
                        dni = parts[1];
                    } else {
                        dni = serialNumber;
                    }
                    creationDate = dateFormat.format(x509Certificate.getNotBefore().getTime());
                    expirationDate = dateFormat.format(x509Certificate.getNotAfter().getTime());
                    try {
                        x509Certificate.checkValidity();
                        valid = "Válido";
                    } catch (CertificateExpiredException e) {
                        valid = "No Válido";
                    }
                    nombreListar = certUserName;
                    if (ruc != null) {
                        if(!ruc.equals("")) {
                            nombreListar += "-" + ruc + "-J";
                        }
                    } else {
                        nombreListar += "-N";
                    }
                    //if(valid.equalsIgnoreCase("Válido")) {
                    Certificate certificate = new Certificate(alias, certUserName,nombreListar,cargo,empresa, creationDate, expirationDate, dni, valid);
                    listCertificates.add(certificate);
                    //}
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return listCertificates;
        } else
            return null;
    }

    public Certificate getCertificateByAlias(String alias) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        List<Certificate> lista = this.getCertificates();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getAlias().equals(alias)) {
                return lista.get(i);
            }
        }
        return null;
    }

    public Certificate getCertificateByName(String name) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        List<Certificate> lista = this.getCertificates();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre_listar().equalsIgnoreCase(name)) {
                return lista.get(i);
            }
        }
        return null;
    }

    public String getCertUserName(X509Certificate certificate) {
        String dn = certificate.getSubjectDN().toString();
        String certUserName = "";
        try {
            LdapName ln = new LdapName(dn);
            for (Rdn rdn : ln.getRdns())
                if (rdn.getType().equalsIgnoreCase("CN")) {
                    certUserName = rdn.getValue().toString();
                    return certUserName;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSerialNumber(X509Certificate certificate) {
        String dn = certificate.getSubjectDN().toString();
        String[] tagserie = new String[]{"SERIALNUMBER"};
        try {
            LdapName ln = new LdapName(dn);
            for (Rdn rdn : ln.getRdns())
                if (rdn.getType().equalsIgnoreCase(tagserie[0])) {
                    return rdn.getValue().toString();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargo(X509Certificate certificate) {
        String dn = certificate.getSubjectDN().toString();
        String[] tagserie = new String[]{"T"};
        try {
            LdapName ln = new LdapName(dn);
            for (Rdn rdn : ln.getRdns())
                if (rdn.getType().equalsIgnoreCase(tagserie[0])) {
                    return rdn.getValue().toString();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEmpresa(X509Certificate certificate) {
        String dn = certificate.getSubjectDN().toString();
        String[] tagserie = new String[]{"O"};
        try {
            LdapName ln = new LdapName(dn);
            for (Rdn rdn : ln.getRdns())
                if (rdn.getType().equalsIgnoreCase(tagserie[0])) {
                    return rdn.getValue().toString();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getOrganizationUnitFromCertificate(X509Certificate cert) {

        String dn = cert.getSubjectDN().toString();
        String[] tagserie = new String[]{"OID.2.5.4.97"};
        try {
            LdapName ln = new LdapName(dn);
            for (Rdn rdn : ln.getRdns())
                if (rdn.getType().equalsIgnoreCase(tagserie[0])) {
                    return rdn.getValue().toString();
                }
        } catch (InvalidNameException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
