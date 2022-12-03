package nye.progtech.game;

import java.sql.SQLException;
import java.util.Scanner;

import jakarta.xml.bind.JAXBException;
import nye.progtech.database.Repository;
import nye.progtech.gamesave.MapSave;
import nye.progtech.gamesave.Save;



/**
 * Tabla.
 */
public class Map {
    private int[][] tabla;
    private boolean win = false;
    private boolean lose = false;
    private final Scanner scanner = new Scanner(System.in);
    private final Step step = new Step();

    /**
     * Létrehozzuk a táblát.
     */
    public void mapBuilder() {
        System.out.print("Milyen legyen a tábla mérete? (minimum 4x4 / maximum 12x12) :: ");
        int nxn;
        while (true) {
            nxn = scanner.nextInt();
            if (nxn < 4) {
                System.out.println();
                System.out.println("Minimum 4x4");
                System.out.print("Írd be újra: ");
            } else if (nxn > 12) {
                System.out.println();
                System.out.println("Maximum 12x12");
                System.out.print("Írd be újra: ");
            } else {
                System.out.println("A tábla mérete  " + nxn + "x" + nxn);
                System.out.println();
                break;
            }
        }
        tabla = new int[nxn][nxn];
        step.mapBuilder(nxn);
    }


    /**
     * Kiirja a tablat.
     */
    private void mapPrinter() {
        for (int[] ints : tabla) {
            for (int j = 0; j < tabla.length; j++) {
                if (ints[j] == 1) {
                    System.out.print("F" + "  ");
                } else if (ints[j] == 2) {
                    System.out.print("D" + "  ");
                } else {
                    System.out.print("_" + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Masolja a tablat.
     */
    private void mapUpdate() {
        tabla = step.mapClone();
    }

    /**
     * Megy a játék amíg nem nyerünk vagy vesztünk.
     */
    public void game(Repository data) throws SQLException, JAXBException {
        win = false;
        lose = false;
        mapUpdate();
        mapPrinter();
        while (true) {
            step.step(step.getFox());
            if (step.isExit()) {
                save(data);
                break;
            }
            data.megtettLepes();
            mapUpdate();
            mapPrinter();
            win(data);
            if (win) {
                break;
            }
            step.step(step.getDog());
            mapUpdate();
            mapPrinter();
            lose(data);
            if (lose) {
                break;
            }
        }
        data.statUpdate();
    }

    /**
     * Nyerés
     */
    private void win(Repository data) {
        if (step.win(step.getFox())) {
            win = true;
            data.win();
            System.out.println();
            System.out.println("A róka győzött!");
            System.out.println("A meccsen megtett lépések száma: " + data.getLepesekSzama());
        }
    }

    /**
     * Vesztés
     */
    private void lose(Repository data) {
        Position fox = step.getFox();
        int[][] tabla = step.mapClone();
        if (!step.balrafel(fox, tabla) && !step.balrale(fox, tabla) && !step.jobbrafel(fox, tabla) && !step.jobbrale(fox, tabla)) {
            lose = true;
            data.lose();
            System.out.println("A kutyák győztek!");
            System.out.println("A meccsen megtett lépések száma: " + data.getLepesekSzama());
        }
    }

    private void save(Repository data) throws JAXBException {
        if (step.isSave()) {
            MapSave bts = new MapSave(getMap());
            Save save = new Save();
            save.save(bts, data.getNev());
            System.out.println("Játékállás elmentve");
        } else {
            System.out.println("A játékállás nem lett elmentve");
        }
    }

    public int[][] getMap() {
        return tabla.clone();
    }

    public void setMap(int[][] tabla) {
        this.tabla = tabla;
    }

    public void load() {
        step.positionfinder(tabla);
    }
}