package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Termin2 extends Termin{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDate datum;
    private DayOfWeek dan;

    public Termin2() {
    }

    public Termin2(LocalDateTime pocetak, LocalDateTime kraj, String prostorija, LocalDateTime startDate, LocalDateTime endDate, LocalDate datum) {
        super(pocetak, kraj, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;
        this.datum = datum;
        this.dan = datum.getDayOfWeek();
    }

    public Termin2(LocalDateTime pocetak, Long trajanje, String prostorija, LocalDateTime startDate, LocalDateTime endDate) {
        super(pocetak, trajanje, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public void setDan(LocalDate datum) {
        this.datum = datum;
        this.dan = datum.getDayOfWeek();
    }
}
