package org.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Raspored {
    private List<Termin> termini;
    private Map<Termin, Boolean> popunjeniTermini;
    private List<Prostorija> prostorije;
    public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    private void inicijalizacija(){

    }

    private void dodajOsobinu(String osobina, String prostorija){
        Prostorija nova = new Prostorija(prostorija);
        nova.dodajOsobinu(osobina);
        if (prostorije.isEmpty()){
            prostorije.add(nova);
        }else if (!prostorije.contains(nova)) {
            prostorije.add(nova);
        } else {
            for(Prostorija p: prostorije){
                if (p.getNaziv().equals(prostorija)) p.dodajOsobinu(osobina);
            }
        }
    }


    private void obrisiTerminIzRasporeda(Termin t){
        if (termini.isEmpty() || !termini.contains(t)) return;
        termini.remove(t);
        popunjeniTermini.put(t, false);
    }
    private void dodajTerminURasporeda(Termin t){
        if (termini.isEmpty() || termini.contains(t)) return;
        termini.add(t);
        popunjeniTermini.put(t, true);
    }
    private void premestiTermin(Termin t, LocalDateTime noviPocetak, LocalDateTime noviKraj){
        obrisiTerminIzRasporeda(t);
        t.setPocetak(noviPocetak);
        t.setKraj(noviKraj);
        dodajTerminURasporeda(t);
    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
