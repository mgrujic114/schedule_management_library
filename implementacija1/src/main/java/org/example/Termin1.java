package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
public class Termin1 extends Termin{
    private DayOfWeek dan;
    private LocalDate datum;

    public Termin1() {
    }

    public Termin1(LocalDateTime pocetak, LocalDateTime kraj, String prostorija) {
        super(pocetak, kraj, prostorija);
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
}
