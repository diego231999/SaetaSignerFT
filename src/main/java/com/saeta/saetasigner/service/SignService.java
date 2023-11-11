package com.saeta.saetasigner.service;

import com.saeta.saetasigner.model.ConfiguracionFirma;
import com.saeta.saetasigner.utils.Certificate;
import com.saeta.saetasigner.utils.CertificateImp;
import com.saeta.saetasigner.utils.PropertyImp;
import com.saeta.saetasigner.utils.SignImp;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SignService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final CertificateImp certificateImp;
    private final FileService fileService = new FileService();
    private final SignImp signImp;

    public SignService() {
        this.signImp = new SignImp();
        this.certificateImp = new CertificateImp();
    }

    public void signFile(ConfiguracionFirma cf, String certificado) throws Exception {

        String origen = "";
        String destino = "";
        String imagenes = "";

        PropertyImp propertyImp = new PropertyImp();
        origen = propertyImp.getProperty("carpetaPorFirmar");
        destino = propertyImp.getProperty("carpetaFirmados");
        imagenes = propertyImp.getProperty("carpetaImagenes");

        ArrayList<File> filesToSign = new ArrayList<>();
        for (String fileName : cf.getNombrePdf()) {
            filesToSign.add(this.fileService.getFileByName(fileName));
        }

        Certificate certificate = null;

        certificate = (this.certificateImp.getCertificateByName(certificado.toString()));
        String signerName = (this.certificateImp.getCertificateByName(certificado.toString())).getNombre();

        String textofirma = "";
        boolean selloTiempo = false;
        boolean ltv = false;
        boolean todasPaginas = false;
        String sTiempoUrl = "", sTiempoUser = "", sTiempoPass = "";


        int indice = 0;
        /*
            if (this.ssignerProperty.firmaSelloTiempo) {
                selloTiempo = true;
                sTiempoUrl = this.ssignerProperty.firmaSelloUrl;
                sTiempoUser = this.ssignerProperty.firmaSelloUser;
                sTiempoPass = this.ssignerProperty.firmaSelloPass;
            }
        */
            /*
            if (this.ssignerProperty.firmaLtv)
                ltv = true;
            if (this.ssignerProperty.todasPaginas)
                todasPaginas = true;
            */




        textofirma = textofirma + "Firmado digitalmente por: " + signerName + "\n";
        if (cf.getEtiqueta().contains("-J")) {
            textofirma = textofirma + "Empresa: " + certificate.getEmpresa() + "\n";
            textofirma = textofirma + "Cargo: " + certificate.getCargo() + "\n";
        }
        textofirma = textofirma + "Fecha/Hora: " + this.dateFormat.format(new Date());

            /*
            if (!this.ssignerProperty.firmaCargo.equals("")) {
                String value = new String(this.ssignerProperty.firmaCargo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                textofirma = textofirma + "Cargo: " + value + "\n";
            }
            if (!this.ssignerProperty.firmaLocacion.equals("")) {
                String value = new String(this.ssignerProperty.firmaLocacion.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                textofirma = textofirma + "Lugar: " + value + "\n";
            }

            if (!this.ssignerProperty.firmaMotivo.equals("")) {
                String value = new String(this.ssignerProperty.firmaMotivo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                textofirma = textofirma + "Motivo: " + value + "\n";
            }
             */

        //textofirma = textofirma + "Fecha/Hora: " + this.dateFormat.format(new Date());

        for (int i = 0; i < cf.getNombrePdf().size(); i++) {
            this.signImp.signFile(
                    Float.parseFloat(cf.getX()),
                    Float.parseFloat(cf.getY()),
                    Float.parseFloat(cf.getAlto()),
                    Float.parseFloat(cf.getAncho()),
                    Integer.parseInt(cf.getPagina()),
                    textofirma,
                    filesToSign.get(i),
                    cf.getNombreImg().get(i),
                    certificate,
                    origen,
                    destino,
                    imagenes);
            i++;
        }
    }
}

