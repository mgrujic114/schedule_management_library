package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
public class Termin2 extends Termin{
    private LocalDate startDate;
    private LocalDate endDate;
    private DayOfWeek dan;
    private LocalTime pocetakVr;
    private LocalTime krajVr;

    public Termin2() {
    }

    public Termin2(LocalDateTime pocetak, LocalDateTime kraj, String prostorija, LocalDate startDate, LocalDate endDate, LocalDate datum) {
        super(pocetak, kraj, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;

        this.pocetakVr = pocetak.toLocalTime();
        this.krajVr = kraj.toLocalTime();

        this.dan = datum.getDayOfWeek();
    }

    public Termin2(LocalDateTime pocetak, Long trajanje, String prostorija, LocalDate startDate, LocalDate endDate) {
        super(pocetak, trajanje, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;
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
