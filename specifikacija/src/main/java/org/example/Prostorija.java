package org.example;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Getter
@Setter
public class Prostorija {
    private String naziv;
//    private int brojMesta;
//    private int brojRacunara;
//    private boolean imaRacunar;
//    private boolean imaProjektor;
//    private boolean imaGrafickaTabla;
//    private List<String> ostaleOsobine;
    private Map<Osobine, String> osobine;

    public Prostorija(String naziv) {
        this.naziv = naziv;
        this.osobine = new HashMap<>();
    }

    public void dodajOsobinu(Osobine osobina, String opis){

        for (Osobine o: Osobine.values()){
            if (osobina.equals(o)) osobine.put(osobina, opis);
        }
//        Scanner sc = new Scanner(System.in);
//        String userInput = "";
//        Integer br = 0;
//        switch (osobina){
//            case BROJ_RACUNARA:
//                System.out.println("Koliko racunara ima u ucionici?");
//                userInput = sc.nextLine();
//                br = 0;
//                try {
//                    br = Integer.parseInt(userInput);
//                } catch (NumberFormatException e){
//                    System.err.println("Lose unet broj");
//                    break;
//                }
//                this.brojRacunara = br;
//                break;
//            case RACUNAR:
//                this.imaRacunar = true;
//                break;
//            case KAPACITET:
//                System.out.println("Koliko mesta ima u ucionici?");
//                userInput = sc.nextLine();
//                br = 0;
//                try {
//                    br = Integer.parseInt(userInput);
//                } catch (NumberFormatException e){
//                    System.err.println("Lose unet broj");
//                    break;
//                }
//                this.brojMesta = br;
//                break;
//            case PROJEKTOR:
//                this.imaProjektor = true;
//                break;
//            case GRAFICKA_TABLA:
//                this.imaGrafickaTabla = true;
//                break;
//            case OSTALO:
//                System.out.println("Koju osobinu zelite da dodate?");
//                userInput = sc.nextLine();
//                this.ostaleOsobine.add(userInput);
//                break;
//            default:
//                System.out.println("ne");
//                break;
//        }
//        sc.close();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof Prostorija)) return false;

        else return ((Prostorija) obj).naziv.equals(this.naziv);
    }

    @Override
    public String toString() {
        return naziv;
    }
}
