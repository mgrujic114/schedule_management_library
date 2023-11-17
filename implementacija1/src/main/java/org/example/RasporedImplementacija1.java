package org.example;

import java.io.IOException;
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
    }


    private List<Termin> izabraniTermini = new ArrayList<>();

    private boolean praznaLista(List<String> lista){
        if (lista.isEmpty() || lista == null) return true;
        else return false;
    }
    private void bezZadatog(List<String> kriterijumiP, List<String> kriterijumiT) {
        if (praznaLista(kriterijumiP)) bezZadateProstorije(kriterijumiT);
        else {
            //po kriterijumima za prostoriju i termin
            // 10 racunara + prof redzic
        }
    }
    private void saObaKriterijuma(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        if (praznaLista(kriterijumiT) && praznaLista(kriterijumiP)) {
            bezProstorijaKriterijuma();
            bezTerminKriterijuma();
        }
        else if (praznaLista(kriterijumiT)) bezTerminKriterijuma();
        else if (praznaLista(kriterijumiP)) bezProstorijaKriterijuma();
        System.out.println(izabraniTermini);
    }

    private void bezZadateProstorije(List<String> kriterijumi) {
        //samo termin kriterijumi
        //vezbe
    }
    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezTerminKriterijuma();
        else {
            //pretraga po terminu i uslovu termina
            //datum+vezbe
        }
        System.out.println(izabraniTermini);
    }
    private void bezZadateProstorije(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, List<String> kriterijumiP) {
        if (praznaLista(kriterijumiT)){
            //pretrazivanje po terminu i uslovima prostorije
            //datum+30mesta
        }
        else {
            //pretrazivanje po terminu, uslovima termina i uslovima prostorije
            //datum+grupa+projektor
        }
    }

    private void bezZadatogVremena(Prostorija p, List<String> kriterijumi) {
        if (praznaLista(kriterijumi)) bezProstorijaKriterijuma();
        else{
            //pretrazivanje i po prostoriji i po kriterijumu
            // raf3+30mesta
        }
    }
    private void bezZadatogVremena(List<String> kriterijumi){
        //samo po uslovima prostorije
        //ima graficku tablu
    }
    private void bezProstorijaKriterijuma() {
        //pretrazivanje po prostoriji
        //raf6
    }

    private void bezTerminKriterijuma() {
        //pretrazivanje po vremenu
        //datum
    }

    @Override
    public void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja) {

    }


}
