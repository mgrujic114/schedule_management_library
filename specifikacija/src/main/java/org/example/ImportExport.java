package org.example;

public abstract class ImportExport {
    public Raspored raspored = Raspored.getInstance();
    public abstract boolean importAction(String fileName, String configPath);
    public abstract boolean exportAction(String path);
}
