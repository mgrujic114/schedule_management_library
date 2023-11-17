package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ImportExportJSON1 extends ImportExport{

    @Override
    public boolean importAction(String fileName, String configPath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            raspored = mapper.readValue(new File(fileName), Raspored.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean exportAction(String path) {
        //nema
        return false;
    }
}
