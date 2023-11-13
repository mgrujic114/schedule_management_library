package org.example;

public abstract class ImportExport {
    public abstract boolean importAction(String fileName);
    public abstract boolean exportAction(String path, String fileName);
}
