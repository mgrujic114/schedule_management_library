package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class Raspored {
    private List<Termin> termini;
    private Map<Termin, Boolean> popunjeniTermini;
    private List<Prostorija> prostorije;
    private LocalDateTime validFrom;
    private LocalDateTime validUntill;
    private ImportExport importExport;
    private Raspored(){
    }
   public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    public List<Termin> getTermini() {
        if (termini == null) termini = new ArrayList<>();
        return termini;
    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
