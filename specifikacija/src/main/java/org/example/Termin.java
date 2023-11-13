package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Termin {
    private LocalDateTime pocetak;
   private LocalDateTime kraj;
   private Prostorija prostorija;
   private List<String> vezaniPodaci;

    public Termin(LocalDateTime pocetak, LocalDateTime kraj, String prostorija) {
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.prostorija = new Prostorija(prostorija);
    }

    public Termin(LocalDateTime pocetak, Long trajanje, String prostorija) {
        this.pocetak = pocetak;
        this.kraj = pocetak.plusMinutes(60*trajanje);
        this.prostorija = new Prostorija(prostorija);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof Termin)) return false;

        else return ((Termin) obj).prostorija.equals(this.prostorija) &&
                    ((Termin) obj).pocetak.equals(this.pocetak) &&
                    ((Termin) obj).kraj.equals(this.kraj);
    }
}
