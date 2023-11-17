package org.example;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dobrodosli u raspored!");
        Scanner sc = new Scanner(System.in);
        String userInput = "hi";
        RasporedHolder r;

        try {
            Class<?> impl = Class.forName("softverske_komponente.RasporedImplementacija");
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
                    "\n5)menjanje termina iz rasporeda\n6)skidanje rasporeda");
            userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    //raspored.initialize();
                    break;
                case "2":
                    System.out.println("Upisite argumente za prestragu:");
                    String argumenti = sc.nextLine();
                    //raspored.izlistajTermine();
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
                default:
                    System.out.println("Majmuneee");
                    break;
            }

        }
    }
}