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

    /*
    private void checksize(int mapSize) throws MapBuilderException {
        if (mapSize < 4) {
            throw new MapBuilderException("A pálya máretánek minimum 4x4 kell lennie");
        }
        if (mapSize > 12) {
            throw new MapBuilderException("A pálya méretének maximum 12x12 kell lennie");
        } else {
            throw new MapBuilderException("A tábla mérete megfelelő");
        }
    }*/

    /**
     * Kiirja a tablat.
     */
    private void mapPrinter() {
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla.length; j++) {
                if (tabla[i][j] == 1) {
                    System.out.print("F" + "  ");
                } else if (tabla[i][j] == 2) {
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
     * Ossze van szedve a jatek folyamata loopolva, amig nem nyerunk v vesztunk.
     */
    public void game(Repository pd) throws SQLException, JAXBException {
        win = false;
        lose = false;
        mapUpdate();
        mapPrinter();
        while (true) {
            step.step(step.getFox());
            if (step.isExit()) {
                save(pd);
                break;
            }
            pd.megtettLepes();
            mapUpdate();
            mapPrinter();
            win(pd);
            if (win) {
                break;
            }
            step.step(step.getDog());
            mapUpdate();
            mapPrinter();
            lose(pd);
            if (lose) {
                break;
            }
        }
        pd.statValtozas();
    }

    /**
     * Megvizsgalja nyertuk-e mar, ha igen elvegzi a muveleteket.
     */
    private void win(Repository pd) {
        if (step.win(step.getFox())) {
            win = true;
            pd.gyozelem();
            System.out.println();
            System.out.println("Győzött a Róka!");
            System.out.println("Ezen a meccsen megtett lépések száma: " + pd.getLepesekSzama());
        }
    }

    /**
     * Megvizsgalja vesztettunk-e mar, ha igen elvegzi a muveleteket.
     */
    private void lose(Repository pd) {
        Position fox = step.getFox();
        int[][] tabla = step.mapClone();
        if (!step.balrafel(fox, tabla) && !step.balrale(fox, tabla) && !step.jobbrafel(fox, tabla) && !step.jobbrale(fox, tabla)) {
            lose = true;
            pd.vereseg();
            System.out.println("Győztek a kutyák!");
            System.out.println("Ezen a meccsen megtett lépések száma: " + pd.getLepesekSzama());
        }
    }

    private void save(Repository pd) throws JAXBException {
        if (step.isSave()) {
            MapSave bts = new MapSave(getMap());
            Save save = new Save();
            save.save(bts, pd.getNev());
            System.out.println("Játékállásod elmentve");
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