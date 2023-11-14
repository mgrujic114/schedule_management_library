package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DodelaTermina {
    private Raspored raspored;

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
}
