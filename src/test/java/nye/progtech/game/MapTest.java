package nye.progtech.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void tablaLetrehoz() {
    }

    @Test
    void jatek() {
    }

    @Test
    void getTabla() {
    }

    @Test
    void setTabla() {
    }

    @Test
    void betoltes() {
    }

    @Test
    void veresegTest() {
        Step s = new Step();
        boolean vereseg=false;
        Position f = new Position(4,7,1);
        int[][] tabla = new int[8][8];
        tabla[4][7]=1;
        tabla[5][6]=2;
        tabla[3][6]=2;

        if (!s.balrafel(f,tabla) && !s.balrale(f,tabla) && !s.jobbrafel(f,tabla) && !s.jobbrale(f,tabla)) {
            vereseg=true;
        }
        assertTrue(vereseg);
    }
}