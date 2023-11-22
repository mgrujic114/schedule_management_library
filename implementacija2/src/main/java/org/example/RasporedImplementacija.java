package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RasporedImplementacija extends RasporedHolder{
    @Override
    public void inicijalizacija() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite pocetak i kraj vazenja rasporeda u obliku: mm/dd/yyyy,mm/dd/yyyy: ");
        String izbor  = sc.nextLine();

        String[] delovi = izbor.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Parsiranje korisničkog unosa u LocalDate objekte
        LocalDate prviDatum = LocalDate.parse(delovi[0], formatter);
        LocalDate drugiDatum = LocalDate.parse(delovi[1], formatter);
        raspored.setVaziOd(prviDatum);
        raspored.setVaziDo(drugiDatum);

        System.out.println("Izaberite tip fajla iz kojeg se vrsi ucitavanje: CSV, JSON");
        izbor = sc.nextLine();
        if (izbor.equalsIgnoreCase("CSV")) raspored.setImportExport(new ImportExportCSV2());
        else if (izbor.equalsIgnoreCase("JSON")) raspored.setImportExport(new ImportExportJSON2());
        System.out.println("Unesite putanju do fajla i konfiguracionog fajla u obliku: putanjaDoFajla");
        izbor = sc.nextLine();

        raspored.getImportExport().importAction(izbor,
                "implementacija2/src/main/resources/config.txt");
                        //implementacija2/src/main/java/org/example/RasporedImplementacija.java
    }

    @Override
    public void download() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Izaberite tip fajla u koji zelite da sacuvate: CSV, PDF");
        String izbor  = sc.nextLine();

        if (izbor.equalsIgnoreCase("CSV")) raspored.setImportExport(new ImportExportCSV2());
        else if (izbor.equalsIgnoreCase("PDF")) raspored.setImportExport(new ImportExportPDF2());
        System.out.println("Unesite putanju do fajla: ");
        izbor = sc.nextLine();

        raspored.getImportExport().exportAction(izbor);
    }
    private LocalDate start = null;
    private LocalDate end = null;

    private DayOfWeek dan = null;

    private LocalTime vremePocetka = null;
    private LocalTime vremeKraja = null;

    private Prostorija p = null;

    @Override
    public void izlistaj() {
        start = null;
        boolean startBool = false;

        end = null;
        boolean endBool = false;

        dan = null;
        boolean danBool = false;

        vremePocetka = null;
        boolean pocetakBool = false;

        vremeKraja = null;
        boolean krajBool = false;

        p = null;
        boolean prostorijaBool = false;

        Scanner sc = new Scanner(System.in);

        System.out.println("Unesite pocetak opsega termina u obliku mm/dd/yyyy");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            start = LocalDate.parse(argument, formatter);
            startBool = true;
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite kraj opsega termina u obliku mm/dd/yyyy");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            end = LocalDate.parse(argument, formatter);
            endBool = true;
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite dan pretrage na engleskom");
        argument = sc.nextLine();
        try {
            dan = DayOfWeek.valueOf(argument.toUpperCase());
            danBool = true;
        } catch (Exception e) {
            System.out.println("Error parsing date. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite pocetak termina u obliku hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            vremePocetka = LocalTime.parse(argument, formatter);
            pocetakBool = true;
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite kraj termina u obliku hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            vremeKraja = LocalTime.parse(argument, formatter);
            krajBool = true;
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite naziv prostorije");
        argument = sc.nextLine();
        if (!argument.isBlank() || !argument.isEmpty()){
            p = new Prostorija(argument);
            prostorijaBool = true;
        }

        System.out.println("zelite li da izlistate slobodne ili zauzete termine?");
        argument = sc.nextLine();

        if (argument.equalsIgnoreCase("slobodne"))
            izlistajSlobodneTermine(startBool, endBool, danBool, pocetakBool, krajBool, prostorijaBool);
        else izlistajZauzeteTermine(startBool, endBool, danBool, pocetakBool, krajBool, prostorijaBool);
        //sc.close();
    }

    private List<Termin> izabraniTermini = new ArrayList<>();
    public void izlistajSlobodneTermine(boolean startBool, boolean endBool, boolean danBool, boolean pocetakBool, boolean krajBool, boolean prostorijaBool) {
        izabraniTermini.clear();
        if (!startBool){
            start = raspored.getVaziOd();
        }
        if (!endBool){
            end = raspored.getVaziDo();
        }
        if (start == null || raspored.getVaziOd().isAfter(start) || raspored.getVaziDo().isBefore(start)) {
            System.out.println("Datum pocetka van opsega vazenja rasporeda");
        }
        if (end == null || raspored.getVaziOd().isAfter(end) || raspored.getVaziDo().isBefore(end)) {
            System.out.println("Datum kraja van opsega vazenja rasporeda");
        }

        if (!pocetakBool) {
            vremePocetka = LocalTime.of(9, 0, 0); // raspored working hours?
        }
        if (!krajBool){
            vremeKraja = LocalTime.of(21, 0, 0); // raspored working hours?
        }

        boolean moze;


        for (Termin termin : raspored.getTermini()) {
            moze = true;
            if (!(termin instanceof Termin2)) {
                moze = false;
            } else {
                Termin2 t2 = (Termin2) termin;
                if (danBool && !t2.getDan().equals(dan)) moze = false;
                else if (prostorijaBool && !t2.getProstorija().equals(p)) moze = false;
            }
            if (moze) izabraniTermini.add(termin);
        }

        Termin2 t = new Termin2();
        t.setProstorija(p);
        t.setDan(dan);
        for (LocalTime vreme = vremePocetka; vreme.isBefore(vremeKraja); vreme = vreme.plusHours(1)) {
            t.setPocetakVr(vreme);
            t.setKrajVr(vreme.plusHours(1));

            moze = true;
            //System.out.println(t);
            Termin2 ter2 = new Termin2();
            for (Termin ter : izabraniTermini) {
                    ter2 = (Termin2) ter;
                    if (danBool && !(ter2.getDan().equals(t.getDan()))) moze = false;
                    else if ((prostorijaBool && !(t.getProstorija().equals(ter.getProstorija())))) moze = false;
                    if (ter2.getPocetakVr().isAfter(t.getPocetakVr())
                            && ter2.getPocetakVr().isBefore(t.getKrajVr()))
                        moze = false; //????????????????????????????????
                    else if (ter2.getKrajVr().isAfter(t.getPocetakVr()) &&
                            ter2.getKrajVr().isBefore(t.getKrajVr())) moze = false;
                    else if (ter2.getPocetakVr().isBefore(t.getPocetakVr()) &&
                            ter2.getKrajVr().isAfter(t.getKrajVr())) moze = false;
                }
                if (moze) System.out.println(ter2.getDan()+" "+t);
            }
    }

    public void izlistajZauzeteTermine(boolean startBool, boolean endBool, boolean danBool, boolean pocetakBool, boolean krajBool, boolean prostorijaBool) {
        izabraniTermini.clear();

        if (!pocetakBool) {
            vremePocetka = LocalTime.of(9, 0, 0); // raspored working hours?
        }
        if (!krajBool){
            vremeKraja = LocalTime.of(21, 0, 0); // raspored working hours?
        }
        if (!startBool){
            start = raspored.getVaziOd();
        }
        if (!endBool){
            end = raspored.getVaziDo();
        }

        if (start == null || raspored.getVaziOd().isAfter(start) || raspored.getVaziDo().isBefore(start)) {
            System.out.println("Datum pocetka van opsega vazenja rasporeda");
        }
        if (end == null || raspored.getVaziOd().isAfter(end) || raspored.getVaziDo().isBefore(end)) {
            System.out.println("Datum kraja van opsega vazenja rasporeda");
        }

        boolean moze;

        for (Termin termin : raspored.getTermini()) {
            moze = true;
            if (!(termin instanceof Termin2)) {
                moze = false;
            } else {
                Termin2 t2 = (Termin2) termin;
                if (danBool && !t2.getDan().equals(dan)) moze = false;
                else if (t2.getStartDate().isBefore(start)) moze = false;
                else if (t2.getEndDate().isAfter(end)) moze = false;
                else if (t2.getPocetakVr().isBefore(vremePocetka)) moze = false;
                else if (t2.getKrajVr().isAfter(vremeKraja)) moze = false;
                else if (prostorijaBool && !t2.getProstorija().equals(p)) moze = false;
            }
            if (moze) izabraniTermini.add(termin);
        }
//        if (startDate == null && endDate == null) {
//            if (p != null) bezZadatogDatuma(p, kriterijumiP);
//            else if (kriterijumiT != null) bezZadatog(kriterijumiP, kriterijumiT);
//            else bezZadatogDatuma(kriterijumiP);
//        }
//        else if (p == null && (startDate != null && endDate != null ) ){
//            if (kriterijumiP == null) bezZadateProstorije(startDate, endDate, kriterijumiT);
//            else bezZadateProstorije(startDate, endDate, kriterijumiT, kriterijumiP);
//        }
//        else saObaKriterijuma(startDate, endDate, kriterijumiT, p, kriterijumiP);
        System.out.println(izabraniTermini);
    }


}

