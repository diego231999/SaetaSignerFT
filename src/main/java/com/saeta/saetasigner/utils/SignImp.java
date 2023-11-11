package com.saeta.saetasigner.utils;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.TextChunk;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.itextpdf.layout.Document;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

public class SignImp {


    public void signFile(
            float x,
            float y,
            float height,
            float width,
            int page,
            String text,
            File fileToSign,
            String imageName,
            com.saeta.saetasigner.utils.Certificate certificado,
            String origen,
            String destino,
            String imagenes) throws Exception {

        try {
            ITSAClient tsaClient = null;
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            KeyStore ks = KeyStore.getInstance("Windows-MY");
            ks.load(null, null);
            PrivateKey key = null;
            Certificate[] chain = null;

            key = (PrivateKey) ks.getKey(certificado.getAlias(), null);

            chain = ks.getCertificateChain(certificado.getAlias());

            String fileName = "";
            int i = fileToSign.getName().lastIndexOf(".");
            fileName = fileToSign.getName().substring(0,i);
            fileName += "[F]";


            // Recibimos como parámetro de entrada el nombre del archivo PDF a firmar
            OutputStream fout = new FileOutputStream(destino + "/" + fileName + ".pdf");
            com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(fileToSign.getAbsolutePath());
            PdfSigner pdfSigner = new PdfSigner(reader, fout, new StampingProperties().useAppendMode());

            //pdfSigner.setCertificationLevel(PdfSigner.CERTIFIED_FORM_FILLING_AND_ANNOTATIONS);

            com.itextpdf.signatures.PdfSignatureAppearance sap = pdfSigner.getSignatureAppearance();



            Rectangle rect = new Rectangle(x, y, width, height);

            if (imageName.equals("") || imageName == null) {
                //return "Es necesario seleccionar una imagen de firma";

            } else {
                //Image image =Image.getInstance(firmaImagen);
                //sap.setImage(image);
                sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
                sap.setSignatureGraphic(ImageDataFactory.create(imagenes + "/" + imageName));

            }
            sap.setLayer2Text(text);
            //sap.setReason(reason);
            //sap.setLocation(location);

            sap.setPageRect(rect).setPageNumber(page);

            PrivateKeySignature pks = new PrivateKeySignature(key, "sha256", "SunMSCAPI");
            //ProviderDigest digest = new ProviderDigest("SunMSCAPI");
            pdfSigner.signDetached(new BouncyCastleDigest(), pks, chain,
                    null, null, tsaClient, 8192 * 2, PdfSigner.CryptoStandard.CADES);

            reader.close();
            fout.flush();
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
