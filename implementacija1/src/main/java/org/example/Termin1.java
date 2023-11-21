package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Setter
@Getter
public class Termin1 extends Termin{
    private DayOfWeek dan;
    private LocalDate datum;
    private LocalTime pocetakVr;
    private LocalTime krajVr;

    public Termin1() {
    }

    public Termin1(LocalDateTime pocetak, LocalDateTime kraj, String prostorija) {
        super(pocetak, kraj, prostorija);
        this.pocetakVr = pocetak.toLocalTime();
        this.krajVr = kraj.toLocalTime();

        this.datum = pocetak.toLocalDate();
        this.dan = datum.getDayOfWeek();
    }

    public Termin1(LocalDateTime pocetak, Long trajanje, String prostorija, DayOfWeek dan) {
        super(pocetak, trajanje, prostorija);
        this.dan = dan;
    }

    public void setDan(LocalDate datum) {
        this.datum = datum;
        this.dan = datum.getDayOfWeek();
    }

    @Override
    public void setPocetak(LocalDateTime pocetak) {
        super.setPocetak(pocetak);
        this.pocetakVr = pocetak.toLocalTime();
    }

    @Override
    public void setKraj(LocalDateTime kraj) {
        super.setKraj(kraj);
        this.krajVr = kraj.toLocalTime();
    }
}
