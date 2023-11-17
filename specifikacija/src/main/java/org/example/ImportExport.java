package org.example;

public abstract class ImportExport {
    public Raspored raspored = Raspored.getInstance();
    public abstract boolean importAction(String fileName, String configPath);
    //csv i json
    public abstract boolean exportAction(String path);
    //pdf i csv/json
}
