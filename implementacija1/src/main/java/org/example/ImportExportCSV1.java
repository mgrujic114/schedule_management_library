package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(mappings.get(-2));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(mappings.get(-1));

        for (CSVRecord record : parser) {
            Termin1 t = new Termin1();

            for (ConfigMapping entry : columnMappings) {
                int columnIndex = entry.getIndex();

                if(columnIndex < 0) continue;

                String columnName = entry.getCustom();
                switch (mappings.get(columnIndex)) {
                    case "place":
                        Prostorija p  = new Prostorija(record.get(columnIndex));
                        raspored.getProstorije().add(p);
                        t.setProstorija(p);
                        break;
                    case "start":
                        LocalTime startTime = LocalTime.parse(record.get(columnIndex), timeFormatter);
                        t.setPocetakVr(startTime);
                        break;
                    case "end":
                        LocalTime endTime = LocalTime.parse(record.get(columnIndex), timeFormatter);
                        t.setKrajVr(endTime);
                        break;
                    case "date":
                        LocalDate datum = LocalDate.parse(record.get(columnIndex), dateFormatter);
                        t.setDatum(datum);
                        break;
                    case "day":
                        DayOfWeek dan = DayOfWeek.valueOf(record.get(columnIndex));
                        t.setDan(dan);
                        break;
                    case "additional1":
                        t.getVezaniPodaci().add(record.get(columnIndex));
                        break;
                    case "additional2":
                        t.getProstorija().dodajOsobinu(Osobine.valueOf(columnName.toUpperCase()), record.get(columnIndex));
                        break;
                }
            }
            t.setPocetak(LocalDateTime.of(t.getDatum(), t.getPocetakVr()));
            t.setKraj((LocalDateTime.of(t.getDatum(), t.getKrajVr())));
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
            if (appointment instanceof Termin1) {
                csvPrinter.printRecord(
                        ((Termin1) appointment).getDan(),
                        ((Termin1) appointment).getPocetakVr(),
                        ((Termin1) appointment).getKrajVr(),
                        appointment.getProstorija()
                );
            } else {
                csvPrinter.printRecord(
                        (appointment).getPocetak(),
                        (appointment).getKraj(),
                        appointment.getProstorija()
                );
            }
        }

        csvPrinter.close();
        fileWriter.close();
    }
}
