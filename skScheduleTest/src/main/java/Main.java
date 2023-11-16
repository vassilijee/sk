
import Specifikacija.SpecifikacijaRasporeda;
import Specifikacija.SpecifikacijaRasporedaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void printMenu(String[] options) {
        System.out.println();

        for (String option : options) {
            System.out.println(option);
        }
        System.out.println();
        System.out.print("Choose your option : ");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        //Implementation1 raspored = new Implementation1();
        //Implementation1 raspored = new Implementation1();
        Class.forName("Implementation2");
        SpecifikacijaRasporeda raspored = SpecifikacijaRasporedaManager.getExporter();
        System.out.println("Dobrodosli u testnu aplikaciju za pravljenje rasporeda. " +
                "\nIzaberite koju implementaciju zelite da koristite: ");
        String[] optionsEntry = {"1\t\t-\timpl1",
                "2\t\t-\timpl2",
                "\"exit\"\t-\tExit",
        };
        Scanner scanner = new Scanner(System.in);
        String option = "a";
        while (!option.equals("exit")) {
            printMenu(optionsEntry);
            option = scanner.nextLine();
            if (option.equals("1")) {
                option = "asd";
                while (!option.equals("back")) {
                    String[] optionsImpl1 = {"1\t - \tinitRaspored()",
                            "2\t - \tloadData()",
                            "3\t - \taddRoom()",
                            "4\t - \taddTermin()",
                            "5\t - \texportData()",
                            "6\t - \tdeleteTermin()",
                            "7\t - \tmoveTermin()",
                            "8\t - \tIspisi trenutni raspored",
                            "\"back\"\t - \tback",
                    };
                    printMenu(optionsImpl1);
                    option = scanner.nextLine();

                    if (option.equals("1")) {
                        System.out.println("initRaspred() - Inicijalizacija rasporeda (ucitavanje metapodataka). ");
                        System.out.println("\nUnesite putanju ka fajlu gde se nalaze metapodaci: ");
                        //option = scanner.nextLine();
                        option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\meta.txt";
                        //option = "/impl2Resources/meta.txt";
                        raspored.initRaspored(option);

                    } else if (option.equals("2")) {
                        System.out.println("loadData() - Ucitavanje rasporeda iz .csv, ili .json fajla." +
                                "\nU fajlu se ne smeju nalaziti termini koji bi bili odbijeni zbog ogranicenja iz metapodataka." +
                                "\nKorisnik mora uneti i konfiguracioni fajl koji opisuje koji format za datume koristi i kako ce se mapirati zaglavlja iz fajla na polja nase klase. ");
                        System.out.println("\nUnesite putanju ka fajlu u kom se nalazi raspored: ");
                        //option = scanner.nextLine();
                        option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\termini.csv";
                        System.out.println("\nUnesite putanju ka konfiguracionom fajlu: ");
                        //String tmpOmption = scanner.nextLine();
                        String tmpOmption = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\config.txt";
                        raspored.loadData(option, tmpOmption);

                    } else if (option.equals("3")) {
                        System.out.println("addRoom() - Ucitavanje novih prostorija/soba/?");
                        System.out.println("\nUnesite naziv: ");
                        option = scanner.nextLine();
                        //option = "RAF100";
                        String tmpOption1 = "";
                        String tmpOption2 = "";
                        Map<String, String> additional = new HashMap<>();
                        while (true) {
                            System.out.println("Unesite naziv dodatka vezanog za sobu (unosom \"done\" ste zavrsili sa unosom): ");
                            tmpOption1 = scanner.nextLine();
                            if (tmpOption1.equals("done"))
                                break;
                            System.out.println("Unesite vrednost za taj dodatak: ");
                            tmpOption2 = scanner.nextLine();
                            additional.put(tmpOption1, tmpOption2);
                        }
                        raspored.addRoom(option, additional);
//                        HashMap<String, String> equipment = new HashMap<>();
//                        equipment.put("mikrafon", "DADA");
//                        equipment.put("Kompjuter", "DADA");
//                        raspored.addRoom("RAF21", equipment);

                    } else if (option.equals("4")) {
//                        Map<String, String> additional = new HashMap<>();
//                        additional.put("PREDMET", "ASDASDASD");
//                        additional.put("PROFESOR", "ASDASD");
//                        raspored.addTermin("30.11.2023 10:31", "30.11.2023 11:40", "RAF21", additional);
                        String start;
                        String end;
                        String roomName;
                        String tmpOption1, tmpOption2;
                        Map<String, String> additional = new HashMap<>();

                        System.out.println("addTermin() - Ucitavanje novih termina u raspored");
                        System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru -  " + raspored.getFormatDatuma().toString());
                        start = scanner.nextLine();
                        //option = "12.12.2023 10:00";
                        System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - 12/31/2023 10:00 ");
                        end = scanner.nextLine();
                        //option = "12.12.2023 11:00";
                        System.out.println("\nUnesite prostoriju u kojoj zelite da zakazete termin: ");
                        roomName = scanner.nextLine();
                        //option = "RAF1";
                        while (true) {
                            System.out.println("Unesite naziv dodatka vezanog za termin (unosom \"done\" ste zavrsili sa unosom): ");
                            tmpOption1 = scanner.nextLine();
                            if (tmpOption1.equals("done"))
                                break;
                            System.out.println("Unesite vrednost za taj dodatak: ");
                            tmpOption2 = scanner.nextLine();
                            additional.put(tmpOption1, tmpOption2);
                        }
                        raspored.addTermin(start, end, roomName, additional);
                    } else if (option.equals("5")) {
//                        //exportTermin()
//                        raspored.exportData("asd.csv");
//                        raspored.exportData("asd.txt");
//                        raspored.exportData("asd.json");
                        System.out.println("exportData() - Eksportovanje je moguce u tri formata: .json, .csv, .txt ");
                        System.out.println("\nUnesite putanju gde zelite da se sacuva fajl: ");
                        //option = scanner.nextLine();
                        option = "/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/export.csv";
                        raspored.exportData(option);
                    } else if (option.equals("6")) {
//                        //deleteTermin()
//                        raspored.deleteTermin("29.10.2023 09:15", "29.10.2023 11:15", "RAF1");
//                        System.out.println("Raspored termina nakon brisania: \n" + raspored.getRaspored());
                        String start;
                        String end;
                        String roomName;

                        System.out.println("deleteTermin() - Brisanje termina");
                        System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru - 31.12.2023 10:00: ");
                        start = scanner.nextLine();
                        //option = "12.12.2023 10:00";
                        System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - 31.12.2023 10:00: ");
                        end = scanner.nextLine();
                        //option = "12.12.2023 11:00";
                        System.out.println("\nUnesite prostoriju u kojoj se odrzava termin: ");
                        roomName = scanner.nextLine();
                        //option = "RAF1";

                        raspored.deleteTermin(start, end, roomName);

                    } else if (option.equals("7")) {

                    } else if (option.equals("8")) {
                        System.out.println(raspored.ispisRasporeda(raspored.getRaspored()));

                    } else if (option.equals("back")) {
                        break;
                    }
                }
//                Implementation1 raspored = new Implementation1();
//                raspored.initRaspored("/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/meta.txt");
//
//                raspored.loadData("/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/terminii.csv", "/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/config.txt");
//                //raspored.loadData("termini.json", "config.txt");
//
//                System.out.println("\nOPIS PROSTORA (METADATA): \n" + raspored);
//                System.out.println("Ucitan raspored: \n" + raspored.getRaspored());
//
//                //addRoom()
//                HashMap<String, String> equipment = new HashMap<>();
//                equipment.put("mikrafon", "DADA");
//                equipment.put("Kompjuter", "DADA");
//                raspored.addRoom("RAF21", equipment);
//
//                //addTermin()
//                raspored.addTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF21; Predmet:Aaaaaaaaa; Profesor:UUUUU AAAAAA");
//                raspored.addTermin("30.12.2023 23:31 - 30.12.2023 23:40; RAF2; Predmet:SK; Profesor:Manojlo Mano");
//
//                //exportTermin()
//                raspored.exportData("/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/export.csv");
//                raspored.exportData("/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/export.txt");
//                //raspored.exportData("export.json");
//
//
//                //deleteTermin()
//                raspored.deleteTermin("30.12.2023 23:31", "30.12.2023 23:40", "RAF2");
//                System.out.println("Raspored termina nakon brisania: \n" + raspored.getRaspored());
//
//                //moveTermin()
//                raspored.moveTermin("30.11.2023 23:31 - 30.11.2023 23:40; RAF21|01.01.2023 10:00 - 01.01.2023 11:00; RAF6");
//                System.out.println("Raspored termina nakon izmene: \n" + raspored.getRaspored());
//
//                //pretragaTermina()
//                Map<String, String> additional = new HashMap<>();
//                additional.put("Profesor", "Nikola Redzic");
//                additional.put("Predmet", "SK");
//                System.out.println("Pronadjeni termini: \n" + raspored.pretragaTermina("29.10.2023 15:15", null, null, additional, null));
            }

        }
    }
}
