package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RasporedImplementacija1 extends RasporedHolder{
    @Override
    public void inicijalizacija() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Izaberite tip fajla iz kojeg se vrsi ucitavanje: CSV, PDF, JSON");
        String izbor  = sc.nextLine();
        if (izbor.toUpperCase().equals("CSV")) raspored.setImportExport(new ImportExportCSV1());
        System.out.println("Unesite putanju do fajla i konfiguracionog fajla u obliku: putanjaDoFajla,putanjaDoKonfiguracije");
        izbor = sc.nextLine();

        raspored.getImportExport().importAction(izbor.split(",")[0], izbor.split(",")[1]);

    }

    @Override
    public void download() {

    }

    @Override
    public void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP) {
        izabraniTermini.clear();
        if (pocetak == null && kraj == null) {
            if (p != null) bezZadatogVremena(p, kriterijumiP);
            else if (kriterijumiT != null) bezZadatog(kriterijumiP, kriterijumiT);
            else bezZadatogVremena(kriterijumiP);
        }
        else if (p == null && (pocetak != null && kraj != null ) ){
            if (kriterijumiP == null) bezZadateProstorije(pocetak, kraj, kriterijumiT);
            else bezZadateProstorije(pocetak, kraj, kriterijumiT, kriterijumiP);
        }
        else saObaKriterijuma(pocetak, kraj, kriterijumiT, p, kriterijumiP);
        System.out.println(izabraniTermini);
    }


    private List<Termin> izabraniTermini = new ArrayList<>();

    private boolean praznaLista(List<String> lista){
        if (lista.isEmpty() || lista == null) return true;
        else return false;
    }
    private void bezZadatog(List<String> kriterijumiP, List<String> kriterijumiT) {
        if (praznaLista(kriterijumiP)) bezZadateProstorije(kriterijumiT);
        else {
            //po kriterijumima za prostoriju i za termin
            // 10 racunara + prof redzic
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
    private void saObaKriterijuma(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        if (praznaLista(kriterijumiT) && praznaLista(kriterijumiP)) {
            bezProstorijaKriterijuma(p);
            bezTerminKriterijuma(pocetak, kraj);
        }
        else if (praznaLista(kriterijumiT)) bezTerminKriterijuma(pocetak, kraj);
        else if (praznaLista(kriterijumiP)) bezProstorijaKriterijuma(p);
    }

    private void bezZadateProstorije(List<String> kriterijumi) {
        //samo termin kriterijumi
        //vezbe
        for (Termin termin: raspored.getTermini()){
            boolean ima = true;
            for (String krit: kriterijumi){
                if (!termin.getVezaniPodaci().contains(krit)) ima = false;
            }
            if (ima) izabraniTermini.add(termin);
        }
    }
    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezTerminKriterijuma(pocetak, kraj);
        else {
            //pretraga po terminu i uslovu termina
            //datum+vezbe
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
                for (String krit: kriterijumi){
                    if (!termin.getVezaniPodaci().contains(krit)) ima = false;
                }
                if (ima) izabraniTermini.add(termin);
            }
        }
    }
    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, List<String> kriterijumiP) {
        if (praznaLista(kriterijumiT)){
            //pretrazivanje po terminu i uslovima prostorije
            //datum+30mesta
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
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
            //pretrazivanje po terminu, uslovima termina i uslovima prostorije
            //datum+grupa+projektor
            for (Termin termin: raspored.getTermini()){
                boolean ima = true;
                if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
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

    private void bezZadatogVremena(Prostorija p, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezProstorijaKriterijuma(p);
        else{
            //pretrazivanje po prostoriji i kriterijumu
            //raf6+30mesta
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
    private void bezZadatogVremena(List<String> kriterijumi){
        //samo po uslovima prostorije
        //ima graficku tablu
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
        //pretrazivanje po prostoriji
        //raf6
        for (Termin termin: raspored.getTermini()){
            if (!termin.getProstorija().equals(p)) continue;
            izabraniTermini.add(termin);
        }
    }

    private void bezTerminKriterijuma(LocalDateTime pocetak, LocalDateTime kraj) {
        //pretrazivanje po vremenu
        for (Termin termin: raspored.getTermini()){
            if (!termin.getPocetak().equals(pocetak) || !termin.getKraj().equals(kraj)) continue;
            izabraniTermini.add(termin);
        }
    }

    @Override
    public void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja) {

    }


}
