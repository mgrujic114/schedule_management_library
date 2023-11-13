package org.example;

import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.util.List;

public abstract class RasporedHolder {
    Raspored raspored = Raspored.getInstance();

    public void dodajProstorijuSaOsobinom(String prostorija, Osobine osobina, Object o){
        Prostorija nova = new Prostorija(prostorija);
        nova.dodajOsobinu(osobina, o);
        if (raspored.getProstorije().isEmpty()){
            raspored.getProstorije().add(nova);
        }else if (!raspored.getProstorije().contains(nova)) {
            raspored.getProstorije().add(nova);
        } else {
            for(Prostorija p: raspored.getProstorije()){
                if (p.getNaziv().equals(prostorija)) p.dodajOsobinu(osobina, o);
            }
        }
    }
    private void dodajProstorijuSaOsobinom(String prostorija, Osobine osobina){
        dodajProstorijuSaOsobinom(prostorija, osobina, null);
    }


    private void obrisiTerminIzRasporeda(Termin t){
        if (raspored.getTermini().isEmpty() || !raspored.getTermini().contains(t)) return;
        raspored.getTermini().remove(t);
        raspored.getPopunjeniTermini().put(t, false);
    }
    private void dodajTerminURasporeda(Termin t){
        if (raspored.getTermini().isEmpty() || raspored.getTermini().contains(t)) return;
        raspored.getTermini().add(t);
        raspored.getPopunjeniTermini().put(t, true);
    }
    private void premestiTermin(Termin t, LocalDateTime noviPocetak, LocalDateTime noviKraj){
        obrisiTerminIzRasporeda(t);
        t.setPocetak(noviPocetak);
        t.setKraj(noviKraj);
        dodajTerminURasporeda(t);
    }
    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj,Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, null, p, kriterijumi);

    }
    private void izlistajTermine(LocalDateTime pocetak, long trajanje, Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), null, p, kriterijumi);

    }
    private void izlistajTermine(Prostorija p, List<String> kriterijumi){
        izlistajTermine(null, null, null, p, kriterijumi);

    }
    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, kriterijumi, null, null);
    }
    private void izlistajTermine(LocalDateTime pocetak, long trajanje, List<String> kriterijumi){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), kriterijumi);
    }

    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj){
        izlistajTermine(pocetak, kraj, null, null, null);
    }
    private void izlistajTermine(LocalDateTime pocetak, long trajanje){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje));
    }


    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){

    }
    private void izlistajTermine(LocalDateTime pocetak, long trajanje, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        izlistajTermine(pocetak, pocetak.plusMinutes(trajanje*60), kriterijumiT, p, kriterijumiP);

    }
    private void izlistajTermine(String dan){
        izlistajTermine(dan, null, null, null, null);
    }
    private void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja){}


}
