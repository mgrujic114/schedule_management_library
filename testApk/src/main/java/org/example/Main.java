package org.example;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dobrodosli u raspored!");
        Scanner sc = new Scanner(System.in);
        String userInput = "hi";
        RasporedHolder r;

        try {
            Class<?> impl = Class.forName("org.example.RasporedImplementacija");
            System.out.println(impl);
            r = (RasporedHolder) impl.getDeclaredConstructor().newInstance();
            System.out.println(r);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(!userInput.equalsIgnoreCase("exit")){
            System.out.println("Izaberite opciju:\n1)ucitavanje rasporeda" +
                    "\n2)filtriranje rasporeda\n3)dodavanje u raspored\n4)brisanje iz rasporeda" +
                    "\n5)menjanje termina iz rasporeda\n6)skidanje rasporeda\n7)dodavanje prostorije sa osobinom" +
                    "\nZa izlaz ukucajte izlaz: exit\n");
            userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    r.inicijalizacija();
                    break;
                case "2":
                    funkcija2(r);
                    break;
                case "3":
                    System.out.println("Navedite termin koji zelite da dodate");
                    String termin = sc.nextLine();
                    //raspored.dodajTermin();
                    break;
                case "4":
                    System.out.println("Navedite termin koji zelite da obrisete");
                    String brisanje = sc.nextLine();
                    //raspored.obrisiTermin();
                    break;
                case "5":
                    System.out.println("Navedite promenu");
                    String promena = sc.nextLine();
                    break;
                case "6":
                    //raspored.download();
                    break;
                case "7":
                    funkcija7(r);
                    break;
                default:
                    System.out.println("Majmuneee");
                    break;
            }
        }
        sc.close();
    }

    private static void funkcija2(RasporedHolder r) {
        System.out.println("Upisite argumente za pretragu:");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter date and time (dd/mm/yyyy HH:mm): ");
        String argumenti = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime pocetak = LocalDateTime.parse(argumenti, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }

        sc.close();
        //raspored.izlistajTermine();
    }

    private static void funkcija7(RasporedHolder r) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Navedite naziv ucionice: ");
        String naziv = sc.nextLine();
        System.out.print("Izaberite osobinu koju dodaete: KAPACITET,\n" +
                "    RACUNAR,\n" +
                "    BROJ_RACUNARA,\n" +
                "    PROJEKTOR,\n" +
                "    GRAFICKA_TABLA,\n" +
                "    OSTALO");
        String osobina = sc.nextLine();
        Osobine o = Osobine.OSTALO;
        try{
            o = Osobine.valueOf(osobina.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user input: " + osobina);
        }
        r.dodajProstorijuSaOsobinom(naziv, o);
        sc.close();
    }
}