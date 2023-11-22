import Specifikacija.SpecifikacijaRasporeda;
import Specifikacija.SpecifikacijaRasporedaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// SVE JE ZAKOMENTARISANO, MORAS DA ODKOMENTARISES STA ZELIS DA KORISTIS CTRL + /
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
                System.out.println("Pokrenuli ste implementaciju1");
                Class.forName("Implementation1");
                raspored = SpecifikacijaRasporedaManager.getExporter();
            } else {
                System.out.println("Pokrenuli ste implementaciju2");
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
                        "11\t - \tIspisi spisak svih prostorija",
                        "\"back\"\t - \tback",
                };
                printMenu(optionsImpl);
                option = scanner.nextLine();
                if (option.equals("1")) {
                    System.out.println("initRaspred() - Inicijalizacija rasporeda (ucitavanje metapodataka). ");
                    System.out.println("\nUnesite putanju ka fajlu gde se nalaze metapodaci: ");

//                    Za rucno kucanje
//                    option = scanner.nextLine();

//                    za Windows apsolutna putanja
//                    option = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl1Resources\\meta.txt";
                    option = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl2Resources\\meta.txt";

//                    za Mac apsolutna putanja
//                    option = "impl1Resources/meta.txt";
//                    option = "impl2Resources/meta.txt";

                    raspored.initRaspored(option);
                } else if (option.equals("2")) {
                    System.out.println("loadData() - Ucitavanje rasporeda iz .csv, ili .json fajla." +
                            "\nU fajlu se ne smeju nalaziti termini koji bi bili odbijeni zbog ogranicenja iz metapodataka." +
                            "\nKorisnik mora uneti i konfiguracioni fajl koji opisuje koji format za datume koristi i kako ce se mapirati zaglavlja iz fajla na polja nase klase. ");
                    System.out.println("\nUnesite putanju ka fajlu u kom se nalazi raspored: ");

//                    za rucno kucanje
//                    option = scanner.nextLine();

//                    za Windows apsolutna putanja
//                    option = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl1Resources\\terminii.csv";
                    option = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl2Resources\\termini.csv";

//                    za Mac apsolutna putanja
//                    option = "impl1Resources/terminii.csv";
//                    option = "impl2Resources/termini.csv";

                    String configPath = "";
                    System.out.println("\nUnesite putanju ka konfiguracionom fajlu: ");
//                    Za rucno kucanje
//                    configPath = scanner.nextLine();

//                    za Windows apsolutna putanja
//                    configPath = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl1Resources\\config.txt";
                    configPath = "C:\\Users\\Lana\\Desktop\\Komponente\\sk\\skScheduleTest\\impl2Resources\\config.txt";

//                    za Mac apsolutna putanja
//                    configPath = "impl1Resources/config.txt";
//                    configPath = "config.txt";

                    System.out.println(raspored.loadData(option, configPath));

                } else if (option.equals("3")) {
                    String tmpOption1 = "";
                    String tmpOption2 = "";
                    Map<String, String> additional = new HashMap<>();

                    System.out.println("addRoom() - Ucitavanje novih prostorija/soba/?");
                    System.out.println("\nUnesite naziv: ");

//                    za rucno kucanje
//                    option = scanner.nextLine();
//                    while (true) {
//                        System.out.println("Unesite naziv dodatka vezanog za sobu (unosom \"done\" ste zavrsili sa unosom): ");
//                        tmpOption1 = scanner.nextLine();
//                        if (tmpOption1.equals("done"))
//                            break;
//                        System.out.println("Unesite vrednost za taj dodatak: ");
//                        tmpOption2 = scanner.nextLine();
//                        additional.put(tmpOption1, tmpOption2);
//                    }

//                    predefinisani unos
                    option = "RAF100";
                    additional.put("Kompjuter", "DA");
                    additional.put("Mikrafon", "NE");

                    if(raspored.addRoom(option, additional)==1)
                        System.out.println("Soba je upisana");
                    else
                        System.out.println("Takva soba vec postoji");
                } else if (option.equals("4")) {
                    String start;
                    String end;
                    String roomName;
                    String tmpOption1, tmpOption2;
                    Map<String, String> additional = new HashMap<>();

//                    za rucni unos
//                    System.out.println("addTermin() - Ucitavanje novih termina u raspored");
//                    System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru -  " + raspored.getFormatDatumaAsString());
//                    start = scanner.nextLine();
//                    System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - 12/31/2023 10:00 ");
//                    end = scanner.nextLine();
//                    System.out.println("\nUnesite prostoriju u kojoj zelite da zakazete termin: ");
//                    roomName = scanner.nextLine();
//                    while (true) {
//                        System.out.println("Unesite naziv dodatka vezanog za termin (unosom \"done\" ste zavrsili sa unosom): ");
//                        tmpOption1 = scanner.nextLine();
//                        if (tmpOption1.equals("done"))
//                            break;
//                        System.out.println("Unesite vrednost za taj dodatak: ");
//                        tmpOption2 = scanner.nextLine();
//                        additional.put(tmpOption1, tmpOption2);
//                    }

//                    predefinisan unos - impl1
                    start = "04.04.2002 10:00";
                    end = "04.04.2002 11:00";
                    roomName = "RAF1";
                    additional.put("Profesor", "asdasd");

//                    predefinisan unos - impl2
//                    start = "12/11/2023 12:00";
//                    end = "01/13/2024 14:00";
//                    roomName = "RAF3";
//                    additional.put("Dan", "petak");
//                    additional.put("Predmet", "OOP");

                    if(raspored.addTermin(start, end, roomName, additional)==1)
                        System.out.println("Termin je dodat");
                    else
                        System.out.println("Taj termin se vremenski vec poklapa sa postojecim terminima ili ne postoji navedena soba ili ste uneli za neradni dan");

                } else if (option.equals("5")) {
                    System.out.println("exportData() - Eksportovanje je moguce u dva formata: .pdf, .csv");
                    System.out.println("\nUnesite putanju gde zelite da se sacuva fajl: ");

//                    za rucno unosenje
//                    option = scanner.nextLine();

//                    predefinisane opcije
//                    option = "export.csv";
                    option = "export.json";
//                    option = "export.txt";
//                    option = "export.pdf";

                    System.out.println(raspored.exportData(option));
                } else if (option.equals("6")) {
                    String start;
                    String end;
                    String roomName;

//                    za unosenje rucno
//                    System.out.println("deleteTermin() - Brisanje termina");
//                    System.out.println("\nUnesite datum i vreme pocetka u formatu kao u primeru - " + raspored.getFormatDatumaAsString() + " : ");
//                    start = scanner.nextLine();
//                    System.out.println("\nUnesite datum i vreme zavrsetka u formatu kao u primeru - " + raspored.getFormatDatumaAsString() + " : ");
//                    end = scanner.nextLine();
//                    System.out.println("\nUnesite prostoriju u kojoj se odrzava termin: ");
//                    roomName = scanner.nextLine();

//                    impl1 primer - brise prvi termin iz loada - RAF1,29.10.2023 09:15,29.10.2023 11:15,UUP,Mladen Jovanovic,DA
                    start = "29.10.2023 09:15";
                    end = "29.10.2023 11:15";
                    roomName = "RAF1";

//                    impl2 primer
//                    start = "12/11/2023 12:00";
////                  end = "01/13/2024 14:00";
////                  roomName = "RAF3";

                    if(raspored.deleteTermin(start, end, roomName)==1)
                        System.out.println("Termin je obrisan");
                    else
                        System.out.println("Takva termin ne postoji u rasporedu");
                } else if (option.equals("7")) {
                    System.out.println("moveTermin() - Pomeranje termina");
                    String podaci;
//                    System.out.println("\nUnesite podatke o postojecem terminu kao na primer: " + raspored.getFormatDatumaAsString() + "; RAF3; petak(ukoliko je grupisajuci raspored uniste dan)|" + raspored.getFormatDatumaAsString() + "; RAF6; sreda(ukoliko je grupisajuci raspored uniste dan)");
//                    podaci = scanner.nextLine();

//                    impl1 primer - pomera drugi termin sa loadData terminii - RAF2,29.10.2023 09:30,29.10.2023 12:30,SK,Bojana Dimic Surla,NE za 04.04.2002
                    podaci = "29.10.2023 09:30 - 29.10.2023 12:30; RAF2|04.04.2002 10:00 - 04.04.2002 11:00; RAF6";

//                    impl2 primer
//                    podaci = "11/12/2023 11:00 - 01/12/2024 13:00; RAF4; petak|11/12/2023 13:00 - 01/12/2024 15:00; RAF2; sreda;

                    if(raspored.moveTermin(podaci)==1)
                        System.out.println("Termin je premesten");
                    else
                        System.out.println("Termin se nije premestio zbog preklapanja sa drugim terminima");
                } else if (option.equals("8")) {
                    String start;
                    String end;
                    String roomName;
                    String pocetak;
                    String kraj;
                    String dan;
                    Map<String, String> additional = new HashMap<>();

//                    rucni unos
//                    System.out.println("pretragaTermina() - Pretraga termina po zeljenom kriterijumu");
//                    System.out.println("\nUnesite datum pocetka u formatu kao u primeru  12/31/2023 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    start = scanner.nextLine();
//                    if (start.equals("skip"))
//                        start = null;
//                    System.out.println("\nUnesite datum kraja u formatu kao u primeru  12/31/2023 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    end = scanner.nextLine();
//                    if (end.equals("skip"))
//                        end = null;
//                    System.out.println("\nUnesite pocetka termina u formatu kao u primeru  10:00 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    pocetak = scanner.nextLine();
//                    if (pocetak.equals("skip"))
//                        pocetak = null;
//                    System.out.println("\nUnesite kraj termina u formatu kao u primeru  10:00 ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    kraj = scanner.nextLine();
//                    if (kraj.equals("skip"))
//                        kraj = null;
//                    System.out.println("\nUnesite ime ucionice ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    roomName = scanner.nextLine();
//                    if (roomName.equals("skip"))
//                        roomName = null;
//                    System.out.println("\nUnesite dan ili rec skip ukoliko ne zelite da pretazujete po ovom kriterijumu");
//                    dan = scanner.nextLine();
//                    if (dan.equals("skip"))
//                        dan = null;
//                    String tmpOption1;
//                    String tmpOption2;
//                    System.out.println("\nSledeci unos je za pretrazivanje po dodatcima ukoliko ne zelite da pretrazujete po ovom kriterijumu napisite skip ");
//                    tmpOption1 = scanner.nextLine();
//                    if (tmpOption1.equals("skip"))
//                        additional = null;
//                    else {
//                        while (true) {
//                            System.out.println("\nUnesite naziv dodatka vezanog za termin (unosom \"done\" ste zavrsili sa unosom): ");
//                            tmpOption1 = scanner.nextLine();
//                            if (tmpOption1.equals("done"))
//                                break;
//                            System.out.println("Unesite vrednost za taj dodatak: ");
//                            tmpOption2 = scanner.nextLine();
//                            additional.put(tmpOption1, tmpOption2);
//                        }
//                    }
//                    System.out.println(raspored.ispisRasporeda(raspored.pretragaTermina(start, end, pocetak, kraj, roomName, additional, dan)));


//                    primer za impl1, vraca samo jedan termin - Termin{start=2023-10-30T15:15, end=2023-10-30T17:15, room=Room{naziv='RAF1', equipment={mikrofon=DA, racunar=DA}}, additional={Profesor=Nikola Redzic, Predmet=SK}}
                    Map<String, String> additionall = new HashMap<>();
                    additionall.put("Profesor", "Nikola Redzic");
                    additionall.put("Predmet", "SK");
                    System.out.println("Pronadjeni termini: \n" + raspored.pretragaTermina("29.10.2023", null, "15:15", null, null, additionall, null));

//                    primer za impl2, vraca jedan termin RAF4,sreda,10/01/2023 10:45,01/05/2024 13:45,VP,Milos Radenkovic,NE
//                    Map<String, String> additionall = new HashMap<>();
//                    additionall.put("Profesor", "Nikola Redzic");
//                    additionall.put("Predmet", "SK");
//                    System.out.println("Pronadjeni termini: \n" + raspored.pretragaTermina(null, null, null, null, "RAF4", null, "sreda"));

                } else if (option.equals("9")) {
                    String start;
                    String end;
                    String roomName;

//                    rucni unos
//                    System.out.println("pretragaZauzetostiUcionica() - Pretraga ukoliko zelite da vidite da li je odredjena ucionica/soba slovodna ili zauzeta u datom terminu");
//                    System.out.println("\nUnesite datum pocetka termina u formatu kao u primeru  12/31/2023 10:00");
//                    start = scanner.nextLine();
//                    System.out.println("\nUnesite datum kraja termina u formatu kao u primeru  12/31/2023 10:00");
//                    end = scanner.nextLine();
//                    System.out.println("\nUnesite naziv ucionice/sobe");
//                    roomName = scanner.nextLine();

//                     impl1 primer
                    roomName = "RAF2";
                    start = "30.10.2023 15:45";
                    end = "30.10.2023 16:00";

//                    impl2 primer
//                    roomName = "RAF3";
//                    start = "10/20/2023 14:20";
//                    end = "10/20/2023 19:10";

                    if (raspored.provaraZauzetostiUcionice(roomName, start, end))
                        System.out.println("\nDati termin je zauzet");
                    else
                        System.out.println("\nDati termin je slobodan");
                } else if (option.equals("10")) {
                    System.out.println(raspored.ispisRasporeda(raspored.getRaspored()));
                } else if (option.equals("11")) {
                    System.out.println(raspored.getSveSobe());
                } else if (option.equals("back")) {
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    // DA LI DA DOAMO OVDE DA SE OBRISE RASPROED I ONDA MOZE DA SE POKRENE DRUGA IMPLEMENTACIJA
                    break;
                }
            }
        }
    }
}
