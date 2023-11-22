
import Specifikacija.SpecifikacijaRasporeda;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SpecifikacijaRasporeda implementacija = new Implementation2();
        implementacija.initRaspored("meta.txt");
        implementacija.loadData("termini.csv", "config.txt");

        System.out.println("\nOPIS PROSTORA (METADATA): \n" + implementacija);

        System.out.println("\nUcitan raspored: \n" + implementacija.ispisRasporeda(implementacija.getRaspored()));
        Map<String, String> a = new HashMap<>();
        a.put("Dan", "petak");
        a.put("Predmet", "OOP");
        implementacija.addTermin("12/11/2023 12:00", "01/13/2024 14:00", "RAF3", a);
        System.out.println("\nRaspored: \n" + implementacija.ispisRasporeda(implementacija.getRaspored()));
        Map<String, String> b = new HashMap<>();
        b.put("racunar", "NE");
        b.put("mikrofon", "DA");
        System.out.println(implementacija.addRoom("RAF30", b));
        System.out.println("\nUcionice: \n" + implementacija.getSveSobe());/*
        implementacija.deleteTermin("12/11/2023 12:00", "01/13/2024 14:00", "RAF3");
        System.out.println("\nRaspored: \n" + implementacija.ispisRasporeda(implementacija.getRaspored()));
        Map<String, String> c = new HashMap<>();
        c.put("Profesor", "Bojana Dimic Surla");
        c.put("Predmet", "UUP");*/
        System.out.println("\nPretraga: \n" + implementacija.ispisRasporeda(implementacija.pretragaTermina(null, null, null, null,"RAF4",null,"sreda")));
        /*System.out.println(implementacija.provaraZauzetostiUcionice("RAF3", "10/20/2023 14:20", "10/20/2023 19:10"));
        implementacija.move("11/12/2023 11:00", "01/12/2024 13:00", "RAF4", "petak", "11/12/2023 13:00", "01/12/2024 15:00", "RAF2", "sreda");
        System.out.println("\nPretraga: \n" + implementacija.ispisRasporeda(implementacija.getRaspored()));
        implementacija.exportData("termini3.csv");
        implementacija.exportData("termini4.pdf");*/
    }
}