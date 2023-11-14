package impl;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        Implementation1 raspored = new Implementation1();

        raspored.loadData("terminii.csv", "config.txt");
        //raspored.loadData("termini.json", "config.txt");

        System.out.println("\nOPIS PROSTORA (METADATA): \n" + raspored);
        System.out.println("Ucitan raspored: \n" + raspored.getRaspored());

        //addRoom()
        HashMap<String, String> equipment = new HashMap<>();
        equipment.put("mikrafon", "DADA");
        equipment.put("Kompjuter", "DADA");
        raspored.addRoom("RAF21", equipment);

        //addTermin()
        raspored.addTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF21; Predmet:Aaaaaaaaa; Profesor:UUUUU AAAAAA");
        raspored.addTermin("30.12.2023 23:31 - 30.12.2023 23:40; RAF2; Predmet:SK; Profesor:Manojlo Mano");

        //exportTermin()
        raspored.exportData("asd.csv");
        raspored.exportData("asd.txt");
        raspored.exportData("asd.json");



        //deleteTermin()
        raspored.deleteTermin("30.12.2023 23:31", "30.12.2023 23:40", "RAF2");
        System.out.println("Raspored termina nakon brisania: \n" + raspored.getRaspored());

        //moveTermin()
        raspored.moveTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF21|01.01.2023 10:00 - 01.01.2023 11:00; RAF6");
        System.out.println("Raspored termina nakon izmene: \n" + raspored.getRaspored());

        //pretragaTermina()
        Map<String, String> additional = new HashMap<>();
        additional.put("Profesor", "Nikola Redzic");
        additional.put("Predmet", "SK");
        System.out.println("Pronadjeni termini: \n" + raspored.pretragaTermina("29.10.2023 15:15", null, null, additional, null));
    }
}
