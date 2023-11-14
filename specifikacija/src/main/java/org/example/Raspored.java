package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class Raspored {
    private List<Termin> termini;
    private Map<Termin, Boolean> popunjeniTermini;
    private List<Prostorija> prostorije;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DodelaTermina dodelaTermina;
    private ImportExport importExport;
    private Raspored(){
        inicijalizacija();
    }
    public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    private void inicijalizacija(){
        dodelaTermina = new DodelaTermina();
        dodelaTermina.setRaspored(this);
    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
