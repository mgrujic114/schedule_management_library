package org.example;

import java.time.LocalDateTime;
import java.util.List;

public abstract class DodelaTermina {
    private List<Termin> termini;

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
        if (termini.isEmpty()) return false;

        Termin termin = new Termin(pocetak, kraj, prostorija);

        if (termini.contains(termin)) return true;

        for (Termin t: termini){
            if (t.equals(termin)) return true;
        }
        return false;
    }

    public boolean imaMesta(Termin t, int brojMesta){
        return imaMesta(t, brojMesta, false);
    }
    public boolean imaMesta(Termin t, int brojMesta, boolean potrebniKomp){
        if (t.getProstorija().getBrojRacunara()>0 && potrebniKomp) return false;
        if (t.getProstorija().getBrojMesta()<brojMesta) return false;
        return true;
    }
}
