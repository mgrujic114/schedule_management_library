package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


@Setter
@Getter
public class Termin1 extends Termin{
    private DayOfWeek dan;

    public Termin1() {
    }

    public Termin1(LocalDateTime pocetak, LocalDateTime kraj, String prostorija, DayOfWeek dan) {
        super(pocetak, kraj, prostorija);
        this.dan = dan;
    }

    public Termin1(LocalDateTime pocetak, Long trajanje, String prostorija, DayOfWeek dan) {
        super(pocetak, trajanje, prostorija);
        this.dan = dan;
    }
}
