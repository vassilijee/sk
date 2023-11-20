
import Specifikacija.SpecifikacijaRasporeda;
import Specifikacija.SpecifikacijaRasporedaManager;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
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
            SpecifikacijaRasporeda raspored;
            if (option.equals("1")) {
                Class.forName("Implementation1");
                raspored = SpecifikacijaRasporedaManager.getExporter();
            }else {
                Class.forName("Implementation2");
                raspored = SpecifikacijaRasporedaManager.getExporter();
            }
            option = "asd";
            while (!option.equals("back")) {
                String[] optionsImpl = {"1\t - \tinitRaspored()",
                        "2\t - \tloadData()",
                        "3\t - \taddRoom()",
                        "4\t - \taddTermin()",
                        "5\t - \texportData()",
                        "6\t - \tdeleteTermin()",
                        "7\t - \tmoveTermin()",
                        "8\t - \tpretragaTermina()",
                        "9\t - \tprovaraZauzetostiUcionice()",
                        "10\t - \tIspisi trenutni raspored",
                        "\"back\"\t - \tback",
                };
                printMenu(optionsImpl);
                option = scanner.nextLine();
                if (option.equals("1")) {
                    System.out.println("initRaspred() - Inicijalizacija rasporeda (ucitavanje metapodataka). ");
                    System.out.println("\nUnesite putanju ka fajlu gde se nalaze metapodaci: ");
                    //option = scanner.nextLine();
                    //option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\meta.txt";
                    option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl1Resources\\meta.txt";
                    //option = "/impl2Resources/meta.txt";
                    raspored.initRaspored(option);

                } else if (option.equals("2")) {
                    System.out.println("loadData() - Ucitavanje rasporeda iz .csv, ili .json fajla." +
                            "\nU fajlu se ne smeju nalaziti termini koji bi bili odbijeni zbog ogranicenja iz metapodataka." +
                            "\nKorisnik mora uneti i konfiguracioni fajl koji opisuje koji format za datume koristi i kako ce se mapirati zaglavlja iz fajla na polja nase klase. ");
                    System.out.println("\nUnesite putanju ka fajlu u kom se nalazi raspored: ");
                    //option = scanner.nextLine();
                    //option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\termini.csv";
                    option = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl1Resources\\terminii.csv";
                    System.out.println("\nUnesite putanju ka konfiguracionom fajlu: ");
                    //String tmpOmption = scanner.nextLine();
//                    String tmpOmption = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl2Resources\\config.txt";
                    String tmpOmption = "C:\\Users\\Lana\\Desktop\\sk_git\\sk\\skScheduleTest\\impl1Resources\\config.txt";
                    raspored.loadData(option, tmpOmption);

                } else if (option.equals("3")) {
                    System.out.println("addRoom() - Ucitavanje novih prostorija/soba/?");
                    System.out.println("\nUnesite naziv: ");
                    option = scanner.nextLine();
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

                } else if (option.equals("4")) {
                    String start;
                    String end;
                    String roomName;
                    String tmpOption1, tmpOption2;
                    Map<String, String> additional = new HashMap<>();
                    System.out.println("addTermin() - Ucitavanje novih termina u raspored");
                    System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru -  " + raspored.getFormatDatumaAsString());
                    start = scanner.nextLine();
                    System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - 12/31/2023 10:00 ");
                    end = scanner.nextLine();
                    System.out.println("\nUnesite prostoriju u kojoj zelite da zakazete termin: ");
                    roomName = scanner.nextLine();
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
                    System.out.println("exportData() - Eksportovanje je moguce u dva formata: .pdf, .csv");
                    System.out.println("\nUnesite putanju gde zelite da se sacuva fajl: ");
                    option = scanner.nextLine();
                   // option = "/Users/Vasilije/Desktop/projekat_git/sk/skScheduleTest/impl1Resources/export.csv";
                    raspored.exportData(option);
                } else if (option.equals("6")) {
                    String start;
                    String end;
                    String roomName;
                    System.out.println("deleteTermin() - Brisanje termina");
                    System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru - 31.12.2023 10:00: ");
                    start = scanner.nextLine();
                    System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - 31.12.2023 10:00: ");
                    end = scanner.nextLine();
                    System.out.println("\nUnesite prostoriju u kojoj se odrzava termin: ");
                    roomName = scanner.nextLine();
                    raspored.deleteTermin(start, end, roomName);

                } else if (option.equals("7")) {
                    System.out.println("moveTermin() - Pomeranje termina");
                    System.out.println("\nUnesite podatke o postojecem terminu kao na primer: 10/01/2023 13:15 - 12/01/2023 15:00; RAF3; petak(ukoliko je grupisajuci raspored uniste dan)|10/01/2023 11:15 - 12/01/2023 13:00; RAF6; sreda(ukoliko je grupisajuci raspored uniste dan)");
                    String podaci = scanner.nextLine();
                    raspored.moveTermin(podaci);
                } else if (option.equals("8")) {
                    String start;
                    String end;
                    String roomName;
                    String pocetak;
                    String kraj;
                    String dan;
                    Map<String, String> additional = new HashMap<>();
                    System.out.println("pretragaTermina() - Pretraga termina po zeljenom kriterijumu");
                    System.out.println("\nUnesite datum pocetka u formatu kao u primeru  12/31/2023 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    start = scanner.nextLine();
                    if(start.equals("skip"))
                        start=null;
                    System.out.println("\nUnesite datum kraja u formatu kao u primeru  12/31/2023 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    end = scanner.nextLine();
                    if(end.equals("skip"))
                        end=null;
                    System.out.println("\nUnesite pocetka termina u formatu kao u primeru  10:00 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    pocetak = scanner.nextLine();
                    if(pocetak.equals("skip"))
                        pocetak=null;
                    System.out.println("\nUnesite kraj termina u formatu kao u primeru  10:00 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    kraj = scanner.nextLine();
                    if(kraj.equals("skip"))
                        kraj=null;
                    System.out.println("\nUnesite ime ucionice ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    roomName = scanner.nextLine();
                    if(roomName.equals("skip"))
                        roomName=null;
                    System.out.println("\nUnesite dan ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
                    dan = scanner.nextLine();
                    if(dan.equals("skip"))
                        dan=null;
                    String tmpOption1;
                    String tmpOption2;
                    System.out.println("\nSledeci unos je za pretrazivanje po dodatcima ukoliko ne zelite da pretrazujete po ovom kriterijumu napisite skip ");
                    tmpOption1 = scanner.nextLine();
                    if (tmpOption1.equals("skip"))
                        additional = null;
                    else {
                        while (true) {
                            System.out.println("\nUnesite naziv dodatka vezanog za termin (unosom \"done\" ste zavrsili sa unosom): ");
                            tmpOption1 = scanner.nextLine();
                            if (tmpOption1.equals("done"))
                                break;
                            System.out.println("Unesite vrednost za taj dodatak: ");
                            tmpOption2 = scanner.nextLine();
                            additional.put(tmpOption1, tmpOption2);
                        }
                    }
                    System.out.println(raspored.ispisRasporeda(raspored.pretragaTermina(start, end, pocetak, kraj, roomName, additional, dan)));

                } else if (option.equals("9")) {
                    String start;
                    String end;
                    String roomName;
                    System.out.println("pretragaZauzetostiUcionica() - Pretraga ukoliko zelite da vidite da li je odredjena ucionica/soba slovodna ili zauzeta u datom terminu");
                    System.out.println("\nUnesite datum pocetka termina u formatu kao u primeru  12/31/2023 10:00");
                    start = scanner.nextLine();
                    System.out.println("\nUnesite datum kraja termina u formatu kao u primeru  12/31/2023 10:00");
                    end = scanner.nextLine();
                    System.out.println("\nUnesite naziv ucionice/sobe");
                    roomName = scanner.nextLine();
                    if(raspored.provaraZauzetostiUcionice(roomName,start,end))
                        System.out.println("\nDati termin je zauzet");
                    else
                        System.out.println("\nDati termin je slobodan");
                } else if (option.equals("10")) {
                    System.out.println(raspored.ispisRasporeda(raspored.getRaspored()));
                } else if (option.equals("back")) {
                    break;
                }
            }
        }
    }
}
