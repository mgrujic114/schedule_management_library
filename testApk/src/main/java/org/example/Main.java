package org.example;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dobrodosli u raspored!");
        Scanner sc = new Scanner(System.in);
        String userInput = "hi";
        RasporedHolder r;

        try {
            Class<?> impl = Class.forName("org.example.RasporedImplementacija");
            r = (RasporedHolder) impl.getDeclaredConstructor().newInstance();
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
                    "\nZa izlaz ukucajte: exit");
            userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    r.inicijalizacija();
                    break;
                case "2":
                    //funkcija2(r);
                    r.izlistaj();
                    break;
                case "3":
                    funkcija3(r);
                    break;
                case "4":
                    funkcija4(r);
                    break;
                case "5":
                    funkcija5(r);
                    break;
                case "6":
                    r.download();
                    break;
                case "7":
                    funkcija7(r);
                    break;
                case "exit":
                    System.out.println("hvala cao");
                    break;
                default:
                    System.out.println("Niste izabrali nista");
                    break;
            }
        }
        sc.close();
    }

    private static void funkcija2(RasporedHolder r) {
        //oba(r);

    }


    private static void funkcija3(RasporedHolder r) {
        LocalDateTime pocetak = LocalDateTime.now();
        LocalDateTime kraj = LocalDateTime.now();

        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite pocetak termina u obliku mm/dd/yyy hh:mm");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            pocetak = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }
        System.out.println("Unesite kraj termina u obliku mm/dd/yyy hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            kraj = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }

        System.out.println("Unesite naziv prostorije");
        argument = sc.nextLine();

        Termin t = new Termin(pocetak, kraj, argument);
        r.dodajTerminURaspored(t);
    }

    private static void funkcija4(RasporedHolder r) {
        LocalDateTime pocetak = LocalDateTime.now();
        LocalDateTime kraj = LocalDateTime.now();

        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite pocetak termina u obliku mm/dd/yyy hh:mm");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            pocetak = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }
        System.out.println("Unesite kraj termina u obliku mm/dd/yyy hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            kraj = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }

        System.out.println("Unesite naziv prostorije");
        argument = sc.nextLine();

        Termin t = new Termin(pocetak, kraj, argument);
        r.obrisiTerminIzRasporeda(t);
    }

    private static void funkcija5(RasporedHolder r) {
        LocalDateTime pocetak = LocalDateTime.now();
        LocalDateTime kraj = LocalDateTime.now();

        LocalDateTime noviPocetak = LocalDateTime.now();
        LocalDateTime noviKraj = LocalDateTime.now();

        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite stari pocetak termina u obliku mm/dd/yyy hh:mm");
        String argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            pocetak = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }
        System.out.println("Unesite stari kraj termina u obliku mm/dd/yyy hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            kraj = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }

        System.out.println("Unesite novi pocetak termina u obliku mm/dd/yyy hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            noviPocetak = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }
        System.out.println("Unesite novi kraj termina u obliku mm/dd/yyy hh:mm");
        argument = sc.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            noviKraj = LocalDateTime.parse(argument, formatter);
        } catch (Exception e) {
            System.out.println("Error parsing date and time. Make sure the format is correct.");
        }

        System.out.println("Unesite naziv prostorije");
        argument = sc.nextLine();

        Termin t = new Termin(pocetak, kraj, argument);
        r.premestiTermin(t, noviPocetak, noviKraj);
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
        System.out.println("karakteristike: ");
        String opis = sc.nextLine();
        r.dodajProstorijuSaOsobinom(naziv, o, opis);
        //sc.close();
    }




//    private static void oba(RasporedHolder r){
//        System.out.println("Trenutno je moguca pretraga po prostoriji i  terminu");
//
//        LocalDateTime pocetak = LocalDateTime.now();
//        LocalDateTime kraj = LocalDateTime.now();
//
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Unesite pocetak termina u obliku mm/dd/yyy hh:mm");
//        String argument = sc.nextLine();
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//            pocetak = LocalDateTime.parse(argument, formatter);
//        } catch (Exception e) {
//            System.out.println("Error parsing date and time. Make sure the format is correct.");
//        }
//        System.out.println("Unesite kraj termina u obliku mm/dd/yyy hh:mm");
//        argument = sc.nextLine();
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//            kraj = LocalDateTime.parse(argument, formatter);
//        } catch (Exception e) {
//            System.out.println("Error parsing date and time. Make sure the format is correct.");
//        }
//
//        System.out.println("Zelite li da dodate jos kriterijuma vezanih za termin?");
//        List<String> kritT = null;
//        argument = sc.nextLine();
//        if (argument.equalsIgnoreCase("da")) {
//            kritT = new ArrayList<>();
//            System.out.println("Za kraj unosa unesite \"kraj\"");
//            while (argument.equalsIgnoreCase("kraj")) {
//                argument = sc.nextLine();
//                kritT.add(argument);
//            }
//        }
//
//        System.out.println("Unesite naziv prostorije");
//        argument = sc.nextLine();
//        Prostorija p = new Prostorija(argument);
//
//        System.out.println("Zelite li da dodate jos kriterijuma?");
//        argument = sc.nextLine();
//        List<String> kritP = null;
//        if (argument.equalsIgnoreCase("da")) {
//            kritP = new ArrayList<>();
//            System.out.println("Za kraj unosa unesite \"kraj\"");
//            while (argument.equalsIgnoreCase("kraj")) {
//                argument = sc.nextLine();
//                kritP.add(argument);
//            }
//        }
//        r.izlistajTermine(pocetak, kraj, kritT, p, kritP);
//        //sc.close();
//    }
//
//    private static void pretragaPoProstoriji(RasporedHolder r){
//        System.out.println("Trenutno je moguca pretraga po prostoriji");
//
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Unesite naziv prostorije");
//        String argument = sc.nextLine();
//        Prostorija p = new Prostorija(argument);
//
//        System.out.println("Zelite li da dodate jos kriterijuma?");
//        argument = sc.nextLine();
//        if (argument.equalsIgnoreCase("da")){
//            List<String> krit = new ArrayList<>();
//            System.out.println("Za kraj unosa unesite \"kraj\"");
//            while (argument.equalsIgnoreCase("kraj")){
//                argument = sc.nextLine();
//                krit.add(argument);
//            }
//            r.izlistajTermine(p, krit);
//        }
//        else r.izlistajTermine(p);
//        //sc.close();
//    }
//
//    private static void pretragaPoTerminu(RasporedHolder r) {
//        System.out.println("Trenutno je moguca pretraga po terminu");
//
//        LocalDateTime pocetak = LocalDateTime.now();
//        LocalDateTime kraj = LocalDateTime.now();
//
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Unesite pocetak termina u obliku mm/dd/yyy hh:mm");
//        String argument = sc.nextLine();
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//            pocetak = LocalDateTime.parse(argument, formatter);
//        } catch (Exception e) {
//            System.out.println("Error parsing date and time. Make sure the format is correct.");
//        }
//        System.out.println("Unesite kraj termina u obliku mm/dd/yyy hh:mm");
//        argument = sc.nextLine();
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//            kraj = LocalDateTime.parse(argument, formatter);
//        } catch (Exception e) {
//            System.out.println("Error parsing date and time. Make sure the format is correct.");
//        }
//
//        System.out.println("Zelite li da dodate jos kriterijuma?");
//        argument = sc.nextLine();
//        if (argument.equalsIgnoreCase("da")){
//            List<String> krit = new ArrayList<>();
//            System.out.println("Za kraj unosa unesite \"kraj\"");
//            while (argument.equalsIgnoreCase("kraj")){
//                argument = sc.nextLine();
//                krit.add(argument);
//            }
//            r.izlistajTermine(pocetak, kraj, krit);
//        }
//        else r.izlistajTermine(pocetak, kraj);
//        //sc.close();
//    }

}