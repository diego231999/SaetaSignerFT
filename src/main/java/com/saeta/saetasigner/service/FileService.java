package com.saeta.saetasigner.service;

import com.saeta.saetasigner.utils.PropertyImp;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {
    public File getFileByName(String fileName){
        PropertyImp propertyImp = new PropertyImp();
        String origen = propertyImp.getProperty("carpetaPorFirmar");
        return new File(origen + "/" + fileName);
    }
}
