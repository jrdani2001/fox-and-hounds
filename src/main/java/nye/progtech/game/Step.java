package nye.progtech.game;


import java.util.Random;
import java.util.Scanner;

/**
 * Lepes.
 */
public class Step {

    private int[][] tabla;
    private static final int FOX = 1;
    private static final int DOG = 2;
    private boolean exit = false;
    private boolean save = false;
    private Position fox;
    private Position[] dog;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    /**
     * létrehozza a mapot és feltolti.
     */
    public void mapBuilder(int n) {
        tabla = new int[n][n];
        int mezo = tabla.length - 1;
        int kutyakSzama = n / 2;
        dog = new Position[kutyakSzama];
        int kutyaSzamlalo = 0;

        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla.length; j++) {
                tabla[i][j] = 0;
            }
        }

        if ((n / 2) % 2 == 0) {
            tabla[mezo][(mezo / 2) + 1] = FOX;
            fox = new Position(mezo, (mezo / 2) + 1, FOX);
            for (int i = 1; i < tabla.length; i += 2) {
                tabla[0][i] = DOG;
                dog[kutyaSzamlalo] = new Position(0, i, DOG);
                kutyaSzamlalo++;
            }
        } else {
            tabla[mezo][mezo / 2] = FOX;
            fox = new Position(mezo, mezo / 2, FOX);
            for (int i = 1; i < tabla.length; i += 2) {
                tabla[0][i] = DOG;
                dog[kutyaSzamlalo] = new Position(0, i, DOG);
                kutyaSzamlalo++;
            }
        }
    }

    /**
     * Pozició keresés.
     *
     * @param deepcopy tábla másolata.
     */
    public void positionfinder(int[][] deepcopy) {
        tabla = deepcopy;
        dog = new Position[tabla.length / 2];
        int kutyaszamlalo = 0;
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla.length; j++) {
                if (tabla[i][j] == DOG) {
                    dog[kutyaszamlalo] = new Position(i, j, DOG);
                    kutyaszamlalo++;
                }
                if (tabla[i][j] == FOX) {
                    fox = new Position(i, j, FOX);
                }
            }
        }
    }

    /**
     * Lép a táblán .
     */
    public void step(Position x) {
        exit = false;
        save = false;

        if (x.tipus == 1) {
            System.out.println("1 = Fel-Balra | 2 = Fel-Jobbra | 3 = Le-Balra | 4 = Le-Jobbra | 9 = Kilépés");
            int lepes = scanner.nextInt() - 1;

            switch (lepes) {
                case 0: {
                    if (balrafel(x, tabla)) {
                        tabla[x.sor - 1][x.oszlop - 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor - 1;
                        x.oszlop = x.oszlop - 1;
                    } else {
                        System.out.println("Helytelen lépés");
                        step(x);
                    }
                }
                break;
                case 1: {
                    if (jobbrafel(x, tabla)) {
                        tabla[x.sor - 1][x.oszlop + 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor - 1;
                        x.oszlop = x.oszlop + 1;
                    } else {
                        System.out.println("Helytelen lépés");
                        step(x);
                    }
                }
                break;
                case 2: {
                    if (balrale(x, tabla)) {
                        tabla[x.sor + 1][x.oszlop - 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor + 1;
                        x.oszlop = x.oszlop - 1;
                    } else {
                        System.out.println("Helytelen lépés");
                        step(x);
                    }
                }
                break;
                case 3: {
                    if (jobbrale(x, tabla)) {
                        tabla[x.sor + 1][x.oszlop + 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor + 1;
                        x.oszlop = x.oszlop + 1;
                    } else {
                        System.out.println("Helytelen lépés");
                        step(x);
                    }
                }
                break;
                case 8: {
                    exit();
                } break;
                default: {
                    System.out.println("Ismeretlen parancs");
                    step(x);
                }
            }
        } else {
            int lepes = random.nextInt(4);
            switch (lepes) {
                case 0: {
                    if (balrafel(x, tabla)) {
                        tabla[x.sor - 1][x.oszlop - 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor - 1;
                        x.oszlop = x.oszlop - 1;
                    } else {
                        int i = random.nextInt(dog.length - 1);
                        step(dog[i]);
                    }
                }
                break;
                case 1: {
                    if (jobbrafel(x, tabla)) {
                        tabla[x.sor - 1][x.oszlop + 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor - 1;
                        x.oszlop = x.oszlop + 1;
                    } else {
                        int i = random.nextInt(dog.length - 1);
                        step(dog[i]);
                    }
                }
                break;
                case 2: {
                    if (balrale(x, tabla)) {
                        tabla[x.sor + 1][x.oszlop - 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor + 1;
                        x.oszlop = x.oszlop - 1;
                    } else {
                        int i = random.nextInt(dog.length - 1);
                        step(dog[i]);
                    }
                }
                break;
                case 3: {
                    if (jobbrale(x, tabla)) {
                        tabla[x.sor + 1][x.oszlop + 1] = x.tipus;
                        tabla[x.sor][x.oszlop] = 0;
                        x.sor = x.sor + 1;
                        x.oszlop = x.oszlop + 1;
                    } else {
                        int i = random.nextInt(dog.length - 1);
                        step(dog[i]);
                    }
                }
                break;
                default: {
                    int i = random.nextInt(dog.length - 1);
                    step(dog[i]);
                }
            }
        }
    }

    /**
     * Lemasolja a tablat.
     */
    public int[][] mapClone() {
        return tabla.clone();
    }

    /**
     * Ha a róka beér akkor igazat ad vissza.
     */
    public boolean win(Position x) {
        if (x.sor == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * balra fel lépés.
     */
    public boolean balrafel(Position x, int[][] tabla) {
        int s = x.sor;
        int o = x.oszlop;
        if (s - 1 >= 0) {
            if (o - 1 >= 0) {
                if (tabla[s - 1][o - 1] == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * balra le lépés.
     */
    public boolean balrale(Position x, int[][] tabla) {
        int s = x.sor;
        int o = x.oszlop;
        if (s + 1 < tabla.length) {
            if (o - 1 >= 0) {
                if (tabla[s + 1][o - 1] == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Jobbra fel lépés.
     */
    public boolean jobbrafel(Position x, int[][] tabla) {
        int s = x.sor;
        int o = x.oszlop;
        if (s - 1 >= 0) {
            if (o + 1 < tabla.length) {
                if (tabla[s - 1][o + 1] == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * jobbra le lépés.
     */
    public boolean jobbrale(Position x, int[][] tabla) {
        int s = x.sor;
        int o = x.oszlop;
        if (s + 1 < tabla.length) {
            if (o + 1 < tabla.length) {
                if (tabla[s + 1][o + 1] == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Kilépés.
     */

    public void exit() {
        System.out.println("Biztosan ki akarsz lépni? 1 = Igen | 2 = Nem");
        int valasz = scanner.nextInt();
        switch (valasz) {
            case 1: {
                exit = true;
                save();
                break;
            }
            case 2: {
            } break;
            default: {
                System.out.println("Ismeretlen parancs");
                exit();
            }
        }
    }

    /**
     * Játék állás mentése.
     */

    public void save() {
        System.out.println("El kívánja menteni a jelenlegi játékállást? 1 = Igen | 2 = Nem");
        int valasz = scanner.nextInt();
        switch (valasz) {
            case 1: {
                save = true;
                break;
            }
            case 2: {
            } break;
            default: {
                System.out.println("Ismeretlen parancs");
                save();
            }
        }
    }

    /**
     * Visszaadja a rokat.
     */
    public Position getFox() {
        return fox;
    }

    /**
     * Vissza ad egy kutyát..
     */
    public Position getDog() {
        int i = random.nextInt(dog.length - 1);
        return dog[i];
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSave() {
        return save;
    }
}