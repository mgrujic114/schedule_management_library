package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Prostorija {
    private String naziv;
    private int brojMesta;
    private boolean imaRacunar;
    private boolean imaProjektor;
    private List<String> ostaleOsobine;

    public Prostorija(String naziv) {
        this.naziv = naziv;
    }

    public void dodajOsobinu(String osobina){
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof Prostorija)) return false;

        else return ((Prostorija) obj).naziv.equals(this.naziv);
    }
}
