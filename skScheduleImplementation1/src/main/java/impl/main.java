package impl;

import impl.csv.ScheduleImportExportCSV;

import java.util.HashMap;

public class main {
    public static void main(String[] args) {
        ScheduleImportExportCSV raspored = new ScheduleImportExportCSV();

        raspored.initRaspored("meta.txt");

        raspored.loadData("termini.csv", "config.txt");

        System.out.println("\nOPIS PROSTORA (METADATA): \n" + raspored);

        System.out.println("\nUcitan raspored: \n" + raspored.getRaspored());

        HashMap<String, String> equipment = new HashMap<>();
        equipment.put("mikorfon", "DADA");
        equipment.put("Kompjuter", "DADA");

        raspored.addRoom("RAFFFFFF racunar:DA mikrafon:DA");
        raspored.addTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF2; Predmet:SK; Profesor:Manojlo Manojlo");
        raspored.addTermin("30.12.2023 23:31 - 30.12.2023 23:40; RAF2; Predmet:SK; Profesor:Manojlo Manojlo");

        raspored.exportData("asd.csv");
    }
}
