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
    public static Raspored getInstance(){
        return InstaceHolder.instance;
    }

    private void inicijalizacija(){

    }

    private static class InstaceHolder{
        private static Raspored instance = new Raspored();
    }
}
