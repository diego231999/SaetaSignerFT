package com.saeta.saetasigner.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class FileImp {
    private final Path originFolder;
    private final Path destinationFolder;
    private final Path signatureImageFolder;

    public FileImp(){
        PropertyImp propertyImp = new PropertyImp();
        this.originFolder = Paths.get(propertyImp.getProperty("carpetaPorFirmar"));
        this.destinationFolder = Paths.get(propertyImp.getProperty("carpetaFirmados"));
        this.signatureImageFolder = Paths.get(propertyImp.getProperty("carpetaImagenes"));

    }


    /**
     * Función que permite guardar un archivo en formato bytes[]
     * @param bytesFile Arreglo de bytes del archivo
     * @param fileName  Nombre del archivo
     * @param folder 1 = Folder origen, 2 = Folder destino, 3 = Folder configuration
     * @throws IOException
     */
    public void saveFile(byte[] bytesFile, String fileName, int folder) throws IOException {
        String path = "";
        switch (folder){
            case 1:
                path = String.valueOf(this.originFolder);
                break;
            case 2:
                path = String.valueOf(this.destinationFolder);
                break;
            case 3:
                path = String.valueOf(this.signatureImageFolder);
                break;
        }
        path = path + '/' + fileName;
        Files.deleteIfExists(Path.of(path));
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(bytesFile);
        fos.close();
    }

    public void saveFileB64(String bytesFile, String fileName, int folder){
        String path = "";
        switch (folder){
            case 1:
                path = String.valueOf(this.originFolder);
                break;
            case 2:
                path = String.valueOf(this.destinationFolder);
                break;
            case 3:
                path = String.valueOf(this.signatureImageFolder);
                break;
        }
        path = path + '/' + fileName;

        String base64String = bytesFile; // Reemplaza esta cadena con tu Base64

        // Decodificar la cadena Base64 a un arreglo de bytes
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Escribir los bytes en un archivo
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(decodedBytes);
            System.out.println("Archivo generado exitosamente: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Función que permite eleiminar un archivo
     * @param fileName Nombre del archivo que se desea eliminar
     * @param folder 1 = Folder de origen, 2 = Folder destino
     * @throws IOException
     */
    public void deleteFile(String fileName, int folder) throws IOException {
        switch (folder){
            case 1:
                Files.deleteIfExists(this.originFolder.resolve(fileName));
                break;
            case 2:
                Files.deleteIfExists(this.destinationFolder.resolve(fileName));
        }
    }

    /**
     * Función que permite obtener el arrglo de bytes de un archivo guardado
     * @param fileName Nombre del archivo
     * @param folder 1 = Folder de origen, 2 = Folder de destino
     * @return
     * @throws IOException
     */
    public byte[] loadFile(String fileName, int folder) throws IOException {
        String path = "";
        switch (folder){
            case 1:
                path = String.valueOf(this.originFolder);
                break;
            case 2:
                path = String.valueOf(this.destinationFolder);
                break;
        }
        java.io.File file = new java.io.File(path + '/' +  fileName);
        if (file.exists()){
            return Files.readAllBytes(file.toPath());
        }else{
            return null;
        }
    }

    public void moveFile(File fileToMove, int folder){
        try{
            this.deleteFile(fileToMove.getName(), folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileToMove.renameTo(new File(this.originFolder + "/" + fileToMove.getName()));
    }

    public void cleanFolder(int folder) throws IOException {
        Path path = null;
        switch (folder){
            case 1:
                path = this.originFolder;
                break;
            case 3:
                path = this.signatureImageFolder;
                break;

        }
        FileUtils.cleanDirectory(new File(String.valueOf(path)));
    }
}
