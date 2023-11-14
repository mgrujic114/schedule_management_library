package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Termin1 extends Termin{
    private DayOfWeek dan;
    public Termin1(LocalDateTime pocetak, LocalDateTime kraj, String prostorija, DayOfWeek dan) {
        super(pocetak, kraj, prostorija);
        this.dan = dan;
    }

    public Termin1(LocalDateTime pocetak, Long trajanje, String prostorija, DayOfWeek dan) {
        super(pocetak, trajanje, prostorija);
        this.dan = dan;
    }
}
