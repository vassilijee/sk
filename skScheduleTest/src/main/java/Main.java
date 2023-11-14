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

    public static void main(String[] args) {
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
                while (true) {

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
