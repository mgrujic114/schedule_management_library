package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RasporedImplementacija extends RasporedHolder{
    @Override
    public void inicijalizacija() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Izaberite tip fajla iz kojeg se vrsi ucitavanje: CSV ili JSON");
        String izbor  = sc.nextLine();
        if (izbor.equalsIgnoreCase("CSV")) raspored.setImportExport(new ImportExportCSV2());
        System.out.println("Unesite putanju do fajla i konfiguracionog fajla u obliku: putanjaDoFajla,putanjaDoKonfiguracije");
        izbor = sc.nextLine();

        raspored.getImportExport().importAction(izbor.split(",")[0], izbor.split(",")[1]);

    }

    @Override
    public void download() {

    }

    @Override
    public void izlistajTermine(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP) {
        izabraniTermini.clear();
        if (startDate == null && endDate == null) {
            if (p != null) bezZadatogDatuma(p, kriterijumiP);
            else if (kriterijumiT != null) bezZadatog(kriterijumiP, kriterijumiT);
            else bezZadatogDatuma(kriterijumiP);
        }
        else if (p == null && (startDate != null && endDate != null ) ){
            if (kriterijumiP == null) bezZadateProstorije(startDate, endDate, kriterijumiT);
            else bezZadateProstorije(startDate, endDate, kriterijumiT, kriterijumiP);
        }
        else saObaKriterijuma(startDate, endDate, kriterijumiT, p, kriterijumiP);
    }


    private List<Termin> izabraniTermini = new ArrayList<>();

    private boolean praznaLista(List<String> lista){
        if (lista.isEmpty() || lista == null) return true;
        else return false;
    }
    private void bezZadatog(List<String> kriterijumiP, List<String> kriterijumiT) {
        if (praznaLista(kriterijumiP)) bezZadateProstorije(kriterijumiT);
        else {
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
            for (String krit: kriterijumiP){
                if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
                else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
                else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
                else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
            }
            for (String krit: kriterijumiT){
                if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
            }
            if (ima) izabraniTermini.add(termin);
            }
        }
    }
    private void saObaKriterijuma(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        if (praznaLista(kriterijumiT) && praznaLista(kriterijumiP)) {
            bezProstorijaKriterijuma(p);
            bezTerminKriterijuma(startDate, endDate);
        }
        else if (praznaLista(kriterijumiT)) bezTerminKriterijuma(startDate,endDate);
        else if (praznaLista(kriterijumiP)) bezProstorijaKriterijuma(p);
        System.out.println(izabraniTermini);
    }

    private void bezZadateProstorije(List<String> kriterijumi) {
        for (Termin termin: raspored.getTermini()){
            boolean ima = true;
            for (String krit: kriterijumi){
                if (!termin.getVezaniPodaci().contains(krit)) ima = false;
            }
            if (ima) izabraniTermini.add(termin);
        }
    }
    private void bezZadateProstorije(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezTerminKriterijuma(startDate,endDate);
        else {
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(startDate) || !termin.getKraj().equals(endDate)) continue;
                for (String krit: kriterijumi){
                    if (!termin.getVezaniPodaci().contains(krit)) ima = false;
                }
                if (ima) izabraniTermini.add(termin);
            }
        }

    }
    private void bezZadateProstorije(LocalDateTime startDate, LocalDateTime endDate, List<String> kriterijumiT, List<String> kriterijumiP) {
        if (praznaLista(kriterijumiT)){
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(startDate) || !termin.getKraj().equals(endDate)) continue;
                for (String krit: kriterijumiP){
                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
                }
                if (ima) izabraniTermini.add(termin);
            }
        }
        else {
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(startDate) || !termin.getKraj().equals(endDate)) continue;
                for (String krit: kriterijumiP){
                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
                }
                for (String krit: kriterijumiT){
                    if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
                }
                if (ima) izabraniTermini.add(termin);
            }
        }
    }

    private void bezZadatogDatuma(Prostorija p, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezProstorijaKriterijuma(p);
        else{
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getProstorija().equals(p)) continue;
                for (String krit: kriterijumi){
                    if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
                    else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
                    else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
                    else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
                }
                if (ima) izabraniTermini.add(termin);
            }
        }
    }
    private void bezZadatogDatuma(List<String> kriterijumi){
        for (Termin termin: raspored.getTermini()){
            boolean ima = true;
            for (String krit: kriterijumi){
                if (krit.contains("racunar") && !imaKomp(termin,  1)) ima = false;
                else if (krit.contains("mesta") && !imaMesta(termin, 30)) ima = false;
                else if (krit.contains("projektor") && !imaProjektor(termin, true)) ima = false;
                else if (!termin.getProstorija().getOstaleOsobine().contains(krit)) ima = false;
            }
            if (ima) izabraniTermini.add(termin);
        }
    }
    private void bezProstorijaKriterijuma(Prostorija p) {
        for (Termin termin: raspored.getTermini()){
            if (!termin.getProstorija().equals(p)) continue;
            izabraniTermini.add(termin);
        }
    }

    private void bezTerminKriterijuma(LocalDateTime startDate, LocalDateTime endDate) {
        for (Termin termin: raspored.getTermini()){
            if (!termin.getPocetak().equals(startDate) || !termin.getKraj().equals(endDate)) continue;
            izabraniTermini.add(termin);
        }
    }

    @Override
    public void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja) {

    }


}

