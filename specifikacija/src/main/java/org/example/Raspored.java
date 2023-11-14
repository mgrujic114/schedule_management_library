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
    private ImportExport importExport;
    private Raspored(){
        inicijalizacija();
    }
    //kada bismo imali jos jedan projekat kako bismo ga nazvali i sta bi onda u implementacijama bio dependecy
    // da li bi bila i specifikacija kao compile time a ovo drugo kao runtime ili je dovoljno da stavimo samo drugi projekat
    public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    private void inicijalizacija(){
    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
