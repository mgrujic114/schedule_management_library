package org.example;

public class ImportExportPDF2 extends ImportExport{
    @Override
    public boolean importAction(String fileName) {
        return false;
    }

    @Override
    public boolean exportAction(String path, String fileName) {
        return false;
    }
}
