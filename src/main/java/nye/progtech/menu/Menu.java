package nye.progtech.menu;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import jakarta.xml.bind.JAXBException;
import nye.progtech.database.Player;
import nye.progtech.database.Repository;
import nye.progtech.game.Map;
import nye.progtech.gamesave.Load;


/**
 * Menu, 2 fuggosege a jatekos-adatbazis es a tabla. Innen indulnak.
 */

public class Menu {
    private final Repository stat;
    private final Map map;
    private final Player player;
    private boolean betoltes = false;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(Repository stat, Player player, Map map) {
        this.stat = stat;
        this.map = map;
        this.player = player;
    }

    /**
     * Inditjuk-e a jatekot, ha igen akkor megkeresi van-e mentesunk,
     * ha igen kiirja a statot, ha nem akkor letrehoz egy profilt.
     */
    public void start() throws SQLException {
        System.out.println("FOX AND HOUNDS\n");
        System.out.print("i = Indítás | n = Kilépés :: ");
        char start;

        while (true) {
            start = scanner.next().charAt(0);
            if (start == 'n' || start == 'N') {
                System.out.println("Viszlát");
                System.exit(0);
            } else if (start == 'i' || start == 'I') {
                System.out.println("Jó játékot!\n");
                player.logIn(scanner);
                if (player.findPlayer()) {
                    stat.osszStat();
                } else {
                    player.ujPlayer();
                }
                break;
            } else {
                System.out.println("Ismeretlen parancs -- i/n");
            }
        }
    }

    /**
     * Menu
     */
    public void menu() throws SQLException, JAXBException {
        while (true) {
            System.out.println();
            System.out.println("1 = Játék indítása");
            System.out.println("2 = Statisztikák");
            System.out.println("3 = Scoreboard");
            System.out.println("4 = Kilépés");
            System.out.println();
            int valasztas = scanner.nextInt();
            switch (valasztas) {
                case 1: {
                    saveGameLoad();
                    if (!betoltes) {
                        map.mapBuilder();
                    } else {
                        //load
                        Load load = new Load();
                        Load.load(stat.getNev(), map);
                        map.load();

                    }
                    map.game(stat);
                }
                break;
                case 2: {
                    stat.osszStat();
                }
                break;
                case 3: {
                    stat.scoreBoard();
                }
                break;
                case 4: {
                    System.out.println("Viszlát");
                    System.exit(0);
                }
                break;
                default: {
                    System.out.println("Ismeretlen parancs\n");
                    menu();
                }
            }
        }
    }

    /**
     * Játek állás betöltése.
     */

    public void saveGameLoad() {
        File xml = new File("src//main//resources//" + stat.getNev() + ".xml");
        int valasz = 2;
        if (xml.exists()) {
            System.out.println("Be akarod töltetni az előző mentést?\n1 = Igen | 2 = Nem");
            valasz = scanner.nextInt();
            switch (valasz) {
                case 1: {
                    System.out.println("Betöltés");
                    betoltes = true;

                } break;
                case 2: {
                    betoltes = false;

                } break;
                default: {
                    System.out.println("Ismeretlen parancs");
                } break;
            }
        }
    }
}