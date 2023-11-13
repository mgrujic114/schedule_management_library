package org.example;

import java.time.LocalDateTime;

public abstract class RasporedHolder {
    Raspored raspored = Raspored.getInstance();



    public void dodajProstorijuSaOsobinom(Osobine osobina, String prostorija, Object o){
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
    private void dodajProstorijuSaOsobinom(Osobine osobina, String prostorija){
        dodajProstorijuSaOsobinom(osobina, prostorija, null);
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
}
