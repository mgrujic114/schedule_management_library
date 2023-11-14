package org.example;

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
        return imaMesta(t, brojMesta, 0);
    }
    public boolean imaMesta(Termin t, int brojMesta, int potrebnihKomp){
        if (t.getProstorija().getBrojRacunara()<potrebnihKomp) return false;
        if (t.getProstorija().getBrojMesta()<brojMesta) return false;
        return true;
    }
    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj,Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, null, p, kriterijumi);

    }
    private void izlistajTermine(LocalDateTime pocetak, long trajanje, Prostorija p, List<String> kriterijumi){
        izlistajTermine(pocetak, pocetak.plusMinutes(60*trajanje), null, p, kriterijumi);

    }
    private void izlistajTermine(List<String> kriterijumiT, Prostorija p, List<String> kriterijumi){
        izlistajTermine(null, null, kriterijumiT, p, kriterijumi);
    }
    private void izlistajTermine(Prostorija p, List<String> kriterijumi){
        izlistajTermine(null, null, null, p, kriterijumi);
    }
    private void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumi){
        izlistajTermine(pocetak, kraj, kriterijumi, null, null);
    }
    private void izlistajTermine(LocalDateTime pocetak, List<String> kriterijumi, LocalDateTime kraj){
        izlistajTermine(pocetak, kraj, null, null, kriterijumi);
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


    public abstract void izlistajTermine(LocalDateTime pocetak, LocalDateTime kraj, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP);
    private void izlistajTermine(LocalDateTime pocetak, long trajanje, List<String> kriterijumiT, Prostorija p, List<String> kriterijumiP){
        izlistajTermine(pocetak, pocetak.plusMinutes(trajanje*60), kriterijumiT, p, kriterijumiP);

    }
    private void izlistajTermine(String dan){
        izlistajTermine(dan, null, null, null, null);
    }
    public abstract void izlistajTermine(String dan, LocalDateTime startDate, LocalDateTime endDate, String satnicaPocetka, String satnicaKraja);


}
