package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Prostorija {
    private String naziv;
    private int brojMesta;
    private int brojRacunara;
    private boolean imaProjektor;
    private boolean imaGrafickaTabla;
    private List<String> ostaleOsobine;

    public Prostorija(String naziv) {
        this.naziv = naziv;
    }

    public void dodajOsobinu(Osobine osobina, Object o){
        switch (osobina){
            case BROJ_RACUNARA:
                this.brojRacunara = (Integer) o;
                break;
            case KAPACITET:
                this.brojMesta = (Integer) o;
                break;
            case PROJEKTOR:
                this.imaProjektor = true;
                break;
            case GRAFICKA_TABLA:
                this.imaGrafickaTabla = true;
                break;
            case OSTALO:
                this.ostaleOsobine.add(o.toString());
                break;
            default:
                System.out.println("ne");
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof Prostorija)) return false;

        else return ((Prostorija) obj).naziv.equals(this.naziv);
    }
}
