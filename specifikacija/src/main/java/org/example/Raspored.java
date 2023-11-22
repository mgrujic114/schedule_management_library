package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class Raspored {
    private List<Termin> termini;
    private Map<Termin, Boolean> popunjeniTermini; //obrisati
    private List<Prostorija> prostorije;
    private ImportExport importExport;
    private LocalDate vaziOd;
    private LocalDate vaziDo;
    private List<LocalDate> izuzetniDani;
    private Raspored(){
    }
   public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    public List<LocalDate> getIzuzetniDani() {
        if (izuzetniDani == null) izuzetniDani = new ArrayList<>();
        return izuzetniDani;
    }

    public List<Termin> getTermini() {
        if (termini == null) termini = new ArrayList<>();
        return termini;
    }

    public List<Prostorija> getProstorije() {
        if (prostorije == null) prostorije = new ArrayList<>();
        return prostorije;
    }

    public Map<Termin, Boolean> getPopunjeniTermini() {
        if (popunjeniTermini == null) popunjeniTermini = new HashMap<>();
        return popunjeniTermini;
    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
