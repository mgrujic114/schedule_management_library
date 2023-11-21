package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ImportExportCSV1 extends ImportExport{
   

    @Override
    public boolean importAction(String fileName, String configPath) {
        try {
            loadApache(fileName, configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void loadApache(String fileName, String configPath) throws IOException {
        List<ConfigMapping> columnMappings = readConfig(configPath);
        Map<Integer, String> mappings = new HashMap<>();
        for(ConfigMapping configMapping : columnMappings) {
            mappings.put(configMapping.getIndex(), configMapping.getOriginal());
        }

        FileReader fileReader = new FileReader(fileName);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mappings.get(-1));

        for (CSVRecord record : parser) {
            Termin1 t = new Termin1();

            for (ConfigMapping entry : columnMappings) {
                int columnIndex = entry.getIndex();

                if(columnIndex == -1) continue;

                String columnName = entry.getCustom();
                switch (mappings.get(columnIndex)) {
                    case "place":
                        Prostorija p  = new Prostorija(record.get(columnIndex));
                        raspored.getProstorije().add(p);
                        t.setProstorija(p);
                        break;
                    case "start":
                        LocalDateTime startDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                        t.setPocetak(startDateTime);
                        LocalDate datum = LocalDate.parse(record.get(columnIndex));
                        t.setDan(datum);
                        break;
                    case "end":
                        LocalDateTime endDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                        t.setKraj(endDateTime);
                        break;
                    case "additional1":
                        t.getVezaniPodaci().add(record.get(columnIndex));
                        break;
                    case "additional2":
                        t.getVezaniPodaci().add(columnName);
                        t.getProstorija().dodajOsobinu(Osobine.valueOf(columnName));
                        break;
                }
            }

            raspored.getTermini().add(t);
        }
    }

    private List<ConfigMapping> readConfig(String configPath) throws FileNotFoundException {
        List<ConfigMapping> mappings = new ArrayList<>();

        File file = new File(configPath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ", 3);

            mappings.add(new ConfigMapping(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2]));
        }

        scanner.close();


        return mappings;
    }

    @Override
    public boolean exportAction(String path) {
        try {
            writeData(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    private void writeData(String path) throws IOException {
        // Create a FileWriter and CSVPrinter
        FileWriter fileWriter = new FileWriter(path);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        for (Termin appointment : raspored.getTermini()) {
            csvPrinter.printRecord(
                    ((Termin1) appointment).getDan(),
                    ((Termin1) appointment).getPocetakVr(),
                    ((Termin1) appointment).getKrajVr(),
                    appointment.getProstorija()
            );
        }

        csvPrinter.close();
        fileWriter.close();
    }
}
