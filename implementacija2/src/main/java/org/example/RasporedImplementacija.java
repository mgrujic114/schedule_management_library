package org.example;

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

    @Override
    public void izlistaj() {
        LocalDate start = null;
        LocalDate end = null;

        LocalDate datum = null;

        LocalTime vremePocetka = null;
        LocalTime vremeKraja = null;

        Prostorija p = null;

        Scanner sc = new Scanner(System.in);

        System.out.println("Unesite pocetak opsega termina u obliku mm/dd/yyyy");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            start = LocalDate.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite kraj opsega termina u obliku mm/dd/yyyy");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            end = LocalDate.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite datum pretrage u obliku mm/dd/yyyy");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            datum = LocalDate.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite pocetak termina u obliku hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            vremePocetka = LocalTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite kraj termina u obliku hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            vremeKraja = LocalTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing time. Make sure the format is correct.");
            //return;
        }

        System.out.println("Unesite naziv prostorije");
        argument = sc.nextLine();
        p = new Prostorija(argument);

        izlistajTermine(start, end, datum, vremePocetka, vremeKraja, p);
        //sc.close();
    }

    private List<Termin> izabraniTermini = new ArrayList<>();

    public void izlistajTermine(LocalDate start, LocalDate end, LocalDate datum, LocalTime pocetak, LocalTime kraj, Prostorija p) {
        izabraniTermini.clear();
        if (start == null || raspored.getVaziOd().isAfter(start) || raspored.getVaziDo().isBefore(start)) {
            System.out.println("Datum pocetka van opsega vazenja rasporeda");
        }
        if (end == null || raspored.getVaziOd().isAfter(end) || raspored.getVaziDo().isBefore(end)) {
            System.out.println("Datum kraja van opsega vazenja rasporeda");
        }

        boolean moze;
        for (Termin termin: raspored.getTermini()){
            moze = true;
            if (! (termin instanceof Termin2)){
                moze = false;
            }
            else {
                Termin2 t2 = (Termin2) termin;
                if (!t2.getDatum().equals(datum)) moze = false;
                else if (t2.getPocetakVr().isBefore(pocetak) || t2.getKrajVr().isAfter(kraj)) moze = false;
                else if (!t2.getProstorija().equals(p)) moze = false;
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


//    private boolean praznaLista(List<String> lista){
//        if (lista == null || lista.isEmpty()) return true;
//        else return false;
//    }
//    private void bezZadatog(List<String> kriterijumiP, List<String> kriterijumiT) {
//        if (praznaLista(kriterijumiP)) bezZadateProstorije(kriterijumiT);
//        else {
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//            for (String krit: kriterijumiP){
//                if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//            }
//            for (String krit: kriterijumiT){
//                if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//            }
//            if (ima) izabraniTermini.add(termin);
//            }
//        }
//    }
//    private void saObaKriterijuma(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
//        if (praznaLista(kriterijumiT) && praznaLista(kriterijumiP)) {
//            bezProstorijaKriterijuma(p);
//            bezTerminKriterijuma(startDate, endDate);
//        }
//        else if (praznaLista(kriterijumiT)) bezTerminKriterijuma(startDate,endDate);
//        else if (praznaLista(kriterijumiP)) bezProstorijaKriterijuma(p);
//        System.out.println(izabraniTermini);
//    }
//
//    private void bezZadateProstorije(List<String> kriterijumi) {
//        for (Termin termin: raspored.getTermini()){
//            boolean ima = true;
//            for (String krit: kriterijumi){
//                if (!termin.getVezaniPodaci().contains(krit)) ima = false;
//            }
//            if (ima) izabraniTermini.add(termin);
//        }
//    }
//    private void bezZadateProstorije(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumi) {
//        if (praznaLista(kriterijumi)) bezTerminKriterijuma(startDate,endDate);
//        else {
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (termin.getPocetak().isBefore(startDate) || termin.getKraj().isAfter(endDate)) continue;
//                for (String krit: kriterijumi){
//                    if (!termin.getVezaniPodaci().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//        }
//
//    }
//    private void bezZadateProstorije(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumiT, List<String> kriterijumiP) {
//        if (praznaLista(kriterijumiT)){
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (termin.getPocetak().isBefore(startDate) || termin.getKraj().isAfter(endDate)) continue;
//                for (String krit: kriterijumiP){
//                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//        }
//        else {
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (termin.getPocetak().isBefore(startDate) || !termin.getKraj().isAfter(endDate)) continue;
//                for (String krit: kriterijumiP){
//                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//                }
//                for (String krit: kriterijumiT){
//                    if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//        }
//    }
//
//    private void bezZadatogDatuma(Prostorija p, List<String> kriterijumi) {
//        if (praznaLista(kriterijumi)) bezProstorijaKriterijuma(p);
//        else{
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (!termin.getProstorija().equals(p)) continue;
//                for (String krit: kriterijumi){
//                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//        }
//    }
//    private void bezZadatogDatuma(List<String> kriterijumi){
//        for (Termin termin: raspored.getTermini()){
//            boolean ima = true;
//            for (String krit: kriterijumi){
//                if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//            }
//            if (ima) izabraniTermini.add(termin);
//        }
//    }
//    private void bezProstorijaKriterijuma(Prostorija p) {
//        for (Termin termin: raspored.getTermini()){
//            if (!termin.getProstorija().equals(p)) continue;
//            izabraniTermini.add(termin);
//        }
//    }
//
//    private void bezTerminKriterijuma(LocalDateTime startDate, LocalDateTime endDate) {
//        for (Termin termin: raspored.getTermini()){
//            if (termin.getPocetak().isBefore(startDate) || termin.getKraj().isAfter(endDate)) continue;
//            izabraniTermini.add(termin);
//        }
//    }

//    @Override
//    public void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja) {
//
//    }


}

