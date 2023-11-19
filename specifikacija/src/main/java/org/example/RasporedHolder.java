package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class RasporedHolder {
    Raspored raspored = Raspored.getInstance();

    public abstract void inicijalizacija();
    public abstract void download();

    /***
     * Funkcija dodaje termin u raspored
     * @param
     */
    public void dodajTerminURaspored(Termin t){

        if (raspored.getTermini().contains(t)){
            System.out.println("termin vec postoji");
            return;
        }
        if (postojiPreklapanje(t.getPocetak(), t.getKraj(), t.getProstorija().getNaziv())) {
            System.out.println("termin je zauzet");
            return;
        }
        raspored.getTermini().add(t);
        raspored.getPopunjeniTermini().put(t, true);
    }

    /***
     * Funkcija brise termin iz rasporeda
     * @param t
     */
    public void obrisiTerminIzRasporeda(Termin t){
        if (raspored.getTermini().isEmpty() || !raspored.getTermini().contains(t)) return;
        raspored.getTermini().remove(t);
        raspored.getPopunjeniTermini().put(t, false);
    }

    /***
     * Funkcija zadati termin najpre brise iz liste termina
     * menja mu vreme
     * a zatim ga dodaje, ako je to moguce
     * @param t
     * @param noviPocetak
     * @param noviKraj
     */
    public void premestiTermin(Termin t, LocalDateTime noviPocetak, LocalDateTime noviKraj){
        obrisiTerminIzRasporeda(t);
        t.setPocetak(noviPocetak);
        t.setKraj(noviKraj);
        dodajTerminURaspored(t);
    }

    /***
     * Funkcija dodaje prostoriju sa prosledjenim osobinama u listu prostorija
     * @param prostorija
     * @param osobina
     */
    public void dodajProstorijuSaOsobinom(String prostorija, Osobine osobina){
        System.out.println("dodajprostoriju");
        Prostorija nova = new Prostorija(prostorija);
        nova.dodajOsobinu(osobina);
        if (raspored.getProstorije().isEmpty()){
            raspored.getProstorije().add(nova);
        }else if (!raspored.getProstorije().contains(nova)) {
            raspored.getProstorije().add(nova);
        } else {
            for(Prostorija p: raspored.getProstorije()){
                if (p.getNaziv().equals(prostorija)) p.dodajOsobinu(osobina);
            }
        }
    }
    /**
     * Funkcija vraca vrednost true u slucaju da prostoji preklapanje, tj da je izabrani termin zauzet,
     * a u suprotnom vrednost false
     * @param pocetak odredjuje pocetak termina koji zelimo da proverimo
     * @param trajanje odredjuje trajanje termina koji zelimo da proverimo
     * @param prostorija odredjuje prostoriju u kojoj bi se odvijao termin koji zelimo da proverimo
     * @return true/false
     */
    public boolean postojiPreklapanje(LocalDateTime pocetak, Long trajanje, String prostorija){
        return postojiPreklapanje(pocetak, pocetak.plusMinutes(60*trajanje), prostorija);
    }
    /**
     * Funkcija vraca vrednost true u slucaju da prostoji preklapanje, tj da je izabrani termin zauzet,
     * a u suprotnom vrednost false
     * @param pocetak odredjuje pocetak termina koji zelimo da proverimo
     * @param kraj odredjuje kraj termina koji zelimo da proverimo
     * @param prostorija odredjuje prostoriju u kojoj bi se odvijao termin koji zelimo da proverimo
     * @return true/false
     */
    public boolean postojiPreklapanje(LocalDateTime pocetak, LocalDateTime kraj, String prostorija){
        if (raspored.getTermini().isEmpty()) return false;

        Termin noviTermin = new Termin(pocetak, kraj, prostorija); ///novi 16.30-17.45
        ///t 17.00-18.00
        if (raspored.getTermini().contains(noviTermin)) return true;

        for (Termin t: raspored.getTermini()){
            if (t.equals(noviTermin)) return true;
            if (t.getProstorija().equals(noviTermin.getProstorija())) {
                if (!(noviTermin.getPocetak().isAfter(t.getPocetak()) &&
                        noviTermin.getPocetak().isBefore(t.getKraj()))) return false;
                else if (!(noviTermin.getKraj().isAfter(t.getPocetak()) &&
                        noviTermin.getKraj().isBefore(t.getKraj()))) return false;
                else if (!(noviTermin.getPocetak().isBefore(t.getPocetak()) &&
                        noviTermin.getKraj().isAfter(t.getKraj()))) return false;
            }
        }
        return false;
    }

    public boolean imaMesta(Termin t, int brojMesta){
        return imaMestaiKomp(t, brojMesta, 0, false);
    }
    public boolean imaProjektor(Termin t, boolean ima){
        return imaMestaiKomp(t, 0, 0,true);
    }
    public boolean imaKomp(Termin t, int brojKomp){
        return imaMestaiKomp(t, 0, brojKomp, false);
    }
    public boolean imaMestaiKomp(Termin t, int brojMesta, int potrebnihKomp, boolean imaProjektor){
        if (potrebnihKomp>0 && t.getProstorija().getBrojRacunara()<potrebnihKomp) return false;
        if (brojMesta>0 && t.getProstorija().getBrojMesta()<brojMesta) return false;
        if (imaProjektor && t.getProstorija().isImaProjektor()) return false;
        return true;
    }

    public void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj,Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, null, p, kriterijumi);

    }
    public void izlistajTermine(LocalDateTime pocetak, long trajanje, Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), null, p, kriterijumi);

    }
    public void izlistajTermine(List<String> kriterijumiT, Prostorija p, List<String> kriterijumi){
        izlistajTermine(null, null, kriterijumiT, p, kriterijumi);
    }
    public void izlistajTermine(Prostorija p, List<String> kriterijumi){
        izlistajTermine(null, null, null, p, kriterijumi);
    }
    public void izlistajTermine(Prostorija p){
        izlistajTermine(null, null, null, p, null);
    }
    public void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, kriterijumi, null, null);
    }
    public void izlistajTermine(LocalDateTime pocetak, List<String> kriterijumi, LocalDateTime kraj){
        izlistajTermine(pocetak, kraj, null, null, kriterijumi);
    }
    public void izlistajTermine(LocalDateTime pocetak, long trajanje, List<String> kriterijumi){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), kriterijumi, null, null);
    }

    public void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj){
        izlistajTermine(pocetak, kraj, null, null, null);
    }
    public void izlistajTermine(LocalDateTime pocetak, long trajanje){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), null, null, null);
    }


    public abstract void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP);
    public void izlistajTermine(LocalDateTime pocetak, long trajanje, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        izlistajTermine(pocetak, pocetak.plusMinutes(trajanje*60), kriterijumiT, p, kriterijumiP);

    }
    public void izlistajTermine(String dan){
        izlistajTermine(dan, null, null, null, null);
    }
    public abstract void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja);


}
