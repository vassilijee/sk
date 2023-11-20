import Specifikacija.SpecifikacijaRasporeda;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        SpecifikacijaRasporeda raspored = new Implementation1();
        raspored.initRaspored("meta.txt");

        raspored.loadData("terminii.csv", "config.txt");
        //raspored.loadData("termini.json", "config.txt");

        System.out.println("\nOPIS PROSTORA (METADATA): \n" + raspored);
        System.out.println("Ucitan raspored: \n" + raspored.getRaspored());
        //System.out.println(raspored.getNeradniDani());

        //addRoom()
        HashMap<String, String> equipment = new HashMap<>();
        equipment.put("mikrafon", "DADA");
        equipment.put("Kompjuter", "DADA");
        raspored.addRoom("RAF20", equipment);

        //addTermin()
        Map<String, String> additional = new HashMap<>();
        additional.put("PREDMET", "UUP");
        additional.put("PROFESOR", "RADOMIR");
        raspored.addTermin("07.01.2023 10:31", "07.01.2023 11:40", "RAF20", additional);
        //raspored.addTermin("30.12.2023 23:31 - 30.12.2023 23:40; RAF2; Predmet:SK; Profesor:Manojlo Mano");

        System.out.println(raspored.getRaspored());

        //exportTermin()
        raspored.exportData("asd.csv");
        raspored.exportData("asd.txt");
//        raspored.exportData("asd.json");
//
//
//
//        //deleteTermin()
//        raspored.deleteTermin("30.12.2023 23:31", "30.12.2023 23:40", "RAF2");
//        System.out.println("Raspored termina nakon brisania: \n" + raspored.getRaspored());
//
//        //moveTermin()
//        raspored.moveTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF21|01.01.2023 10:00 - 01.01.2023 11:00; RAF6");
//        System.out.println("Raspored termina nakon izmene: \n" + raspored.getRaspored());
//
//      pretragaTermina()
        Map<String, String> additionall = new HashMap<>();
        additionall.put("Profesor", "Nikola Redzic");
        additionall.put("Predmet", "SK");
        System.out.println("Pronadjeni termini: \n" + raspored.pretragaTermina("29.10.2023", null, "15:15", null, null, additionall, null));


        //System.out.println(raspored.provaraZauzetostiUcionice("RAF2", "30.10.2023 15:45", "31.10.2023 16:00"));
    }
}
