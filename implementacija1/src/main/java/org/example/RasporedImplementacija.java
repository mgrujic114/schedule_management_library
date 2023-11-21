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
        if (izbor.equalsIgnoreCase("CSV")) raspored.setImportExport(new ImportExportCSV1());
        else if (izbor.equalsIgnoreCase("JSON")) raspored.setImportExport(new ImportExportJSON1());

        System.out.println("Unesite putanju do fajla i konfiguracionog fajla u obliku: putanjaDoFajla");
        izbor = sc.nextLine();

        raspored.getImportExport().importAction(izbor.split(",")[0],
                                                "implementacija1/src/main/resources/config.txt");
                                                //implementacija1/src/main/java/org/example/RasporedImplementacija.java

    }

    @Override
    public void download() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Izaberite tip fajla u koji zelite da sacuvate: CSV, PDF");
        String izbor  = sc.nextLine();

        if (izbor.equalsIgnoreCase("CSV")) raspored.setImportExport(new ImportExportCSV1());
        else if (izbor.equalsIgnoreCase("JSON")) raspored.setImportExport(new ImportExportPDF1());
        System.out.println("Unesite putanju do fajla: ");
        izbor = sc.nextLine();

        raspored.getImportExport().exportAction(izbor.split(",")[0]);
    }

    private LocalDate datum;
    private LocalTime vremePocetka;
    private LocalTime vremeKraja;
    private Prostorija p;
    @Override
    public void izlistaj() {
        datum = null;
        boolean datumBool = false;

        vremePocetka = null;
        boolean pocetakBool = false;
        vremeKraja = null;
        boolean krajBool = false;

        p = null;
        boolean prostorijaBool = false;

        Scanner sc = new Scanner(System.in);

        System.out.println("Unesite datum pretrage u obliku mm/dd/yyyy");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            datum = LocalDate.parse(argument, formatter);
            datumBool = true;
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
        if (!argument.isEmpty() && !argument.isBlank()) {
            p = new Prostorija(argument);
            prostorijaBool = true;
        }

        izlistajZauzeteTermine(datumBool, pocetakBool, krajBool, prostorijaBool);
        izlistajSlobodneTermine(datumBool, pocetakBool, krajBool, prostorijaBool);
        //sc.close();
        //System.out.println(izabraniTermini);
    }

    private List<Termin> izabraniTermini = new ArrayList<>();

    public void izlistajSlobodneTermine(boolean datumBool, boolean pocetakBool, boolean krajBool, boolean prostorijaBool) {

        Termin t = new Termin();
        boolean moze = true;
        List<Termin> novi = new ArrayList<>();

        for (LocalTime vreme = vremePocetka; vreme.isBefore(vremeKraja); vreme = vreme.plusHours(1)){
            t.setPocetak(LocalDateTime.of(datum, vreme));
            t.setKraj(LocalDateTime.of(datum, vreme.plusHours(1)));
            t.setProstorija(p);
            moze = true;
            //System.out.println(t);
            for (Termin ter: izabraniTermini){
                if (t.getProstorija().equals(ter.getProstorija())) {
                    if (ter.getPocetak().isAfter(t.getPocetak())
                            && ter.getPocetak().isBefore(t.getKraj())) moze = false; //????????????????????????????????
                    else if (ter.getKraj().isAfter(t.getPocetak()) &&
                            ter.getKraj().isBefore(t.getKraj())) moze = false;
                    else if (ter.getPocetak().isBefore(t.getPocetak()) &&
                            ter.getKraj().isAfter(t.getKraj())) moze = false;
                } else moze = false;
            }
            if (moze) System.out.println(t);
        }

    }

    public void izlistajZauzeteTermine(boolean datumBool, boolean pocetakBool, boolean krajBool, boolean prostorijaBool) {
        izabraniTermini.clear();
        if (datum == null || raspored.getVaziOd().isAfter(datum) || raspored.getVaziDo().isBefore(datum)) {
            System.out.println("Datum van opsega vazenja rasporeda");

        }
        if (!pocetakBool) {
            vremePocetka = LocalTime.of(9, 0, 0); // raspored working hours?
        }
        if (!krajBool){
            vremeKraja = LocalTime.of(21, 0, 0); // raspored working hours?
        }

        boolean moze;

        for (Termin termin: raspored.getTermini()){
            moze = true;
            if (! (termin instanceof Termin1)){
                moze = false;
            }

            else {
                Termin1 t1 = (Termin1) termin;
                if (datumBool && !t1.getDatum().equals(datum)) moze = false;
                else if (t1.getPocetakVr().isBefore(vremePocetka)) moze = false;
                else if (t1.getKrajVr().isAfter(vremeKraja)) moze = false;
                else if (prostorijaBool && !t1.getProstorija().equals(p)) moze = false;
            }
            if (moze) izabraniTermini.add(termin);
        }

//        if (startDate == null && endDate == null) {
//            if (p != null) bezZadatogDatuma(p, kriterijumiP);
//            else if (kriterijumiT != null) bezZadatog(kriterijumiP, kriterijumiT);
//            else bezZadatogDatuma(kriterijumiP);
//        }

//
//
//        else if (p == null && (startDate != null && endDate != null ) ){
//            if (kriterijumiP == null) bezZadateProstorije(startDate, endDate, kriterijumiT);
//            else bezZadateProstorije(startDate, endDate, kriterijumiT, kriterijumiP);
//        }
//        else saObaKriterijuma(startDate, endDate, kriterijumiT, p, kriterijumiP);
    }

//    private boolean praznaLista(List<String> lista){
//        if (lista.isEmpty() || lista == null) return true;
//        else return false;
//    }
//    private void bezZadatog(List<String> kriterijumiP, List<String> kriterijumiT) {
//        if (praznaLista(kriterijumiP)) bezZadateProstorije(kriterijumiT);
//        else {
//            //po kriterijumima za prostoriju i za termin
//            // 10 racunara + prof redzic
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
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
//
//        }
//    }
//    private void saObaKriterijuma(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
//        if (praznaLista(kriterijumiT) && praznaLista(kriterijumiP)) {
//            bezProstorijaKriterijuma(p);
//            bezTerminKriterijuma(pocetak, kraj);
//        }
//        else if (praznaLista(kriterijumiT)) bezTerminKriterijuma(pocetak, kraj);
//        else if (praznaLista(kriterijumiP)) bezProstorijaKriterijuma(p);
//    }
//
//    private void bezZadateProstorije(List<String> kriterijumi) {
//        //samo termin kriterijumi
//        //vezbe
//        for (Termin termin: raspored.getTermini()){
//            boolean ima = true;
//            for (String krit: kriterijumi){
//                if (!termin.getVezaniPodaci().contains(krit)) ima = false;
//            }
//            if (ima) izabraniTermini.add(termin);
//        }
//    }
//    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi) {
//        if (praznaLista(kriterijumi)) bezTerminKriterijuma(pocetak, kraj);
//        else {
//            //pretraga po terminu i uslovu termina
//            //datum+vezbe
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
//                for (String krit: kriterijumi){
//                    if (!termin.getVezaniPodaci().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//        }
//    }
//    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, List<String> kriterijumiP) {
//        if (praznaLista(kriterijumiT)){
//            //pretrazivanje po terminu i uslovima prostorije
//            //datum+30mesta
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
//                for (String krit: kriterijumiP){
//                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
//                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
//                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
//                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
//                }
//                if (ima) izabraniTermini.add(termin);
//            }
//
//        }
//        else {
//            //pretrazivanje po terminu, uslovima termina i uslovima prostorije
//            //datum+grupa+projektor
//            for (Termin termin: raspored.getTermini()){
//                boolean ima = true;
//                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
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
//    private void bezZadatogVremena(Prostorija p, List<String> kriterijumi) {
//        if (praznaLista(kriterijumi)) bezProstorijaKriterijuma(p);
//        else{
//            //pretrazivanje po prostoriji i kriterijumu
//            //raf6+30mesta
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
//
//        }
//    }
//    private void bezZadatogVremena(List<String> kriterijumi){
//        //samo po uslovima prostorije
//        //ima graficku tablu
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
//        //pretrazivanje po prostoriji
//        //raf6
//        for (Termin termin: raspored.getTermini()){
//            if (!termin.getProstorija().equals(p)) continue;
//            izabraniTermini.add(termin);
//        }
//    }
//
//    private void bezTerminKriterijuma(LocalDateTime pocetak, LocalDateTime kraj) {
//        //pretrazivanje po vremenu
//        for (Termin termin: raspored.getTermini()){
//            if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
//            izabraniTermini.add(termin);
//        }
//    }

}
