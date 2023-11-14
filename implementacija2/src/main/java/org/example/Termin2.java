package org.example;

import java.time.LocalDateTime;

public class Termin2 extends Termin{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public Termin2(LocalDateTime pocetak, LocalDateTime kraj, String prostorija, LocalDateTime startDate, LocalDateTime endDate) {
        super(pocetak, kraj, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Termin2(LocalDateTime pocetak, Long trajanje, String prostorija, LocalDateTime startDate, LocalDateTime endDate) {
        super(pocetak, trajanje, prostorija);
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
