package nye.progtech.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StepTest {

    @Mock
    Step s;
    Position x;


    @BeforeEach
    void beforeEach() {
        s = new Step();
        s.mapBuilder(8);
        x = null;
    }

    @Test
    void tablaLetrehozTest() {
        s.mapBuilder(8);
        int[][] tabla = s.mapClone();
        boolean elhelyezes = tabla[0][1] == 2 && tabla[7][4] == 1;
        assertTrue(elhelyezes);
    }

    @Test
    void lepTest() {
        x = new Position(4,4,2);
        int[][] tabla = s.mapClone();
        short helyes=0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        tabla[4][4] = 2;
        s.step(x);

        if(x.sor!=4&&x.oszlop!=4) {
            helyes++;
        }
        if(tabla[4][4]==0) {
            helyes++;
        }

        assertEquals(2, helyes);
    }

    @Test
    void getFoxTest() {
        Position fox = s.getFox();
        assertEquals(fox, s.getFox());
    }

    @Test
    void tablaCloneTest() {
        int[][] tabla = s.mapClone();
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla.length; j++) {
                tabla[i][j] = 0;
            }
        }
        int[][] klon = tabla.clone();
        boolean egyezik = true;

        for (int i = 0; i < klon.length; i++) {
            for (int j = 0; j < klon.length; j++) {
                if (klon[i][j] != tabla[i][j]) {
                    egyezik = false;
                    break;
                }
            }
        }
        assertTrue(egyezik);
    }

    @Test
    void gyozelemTest_True() {
        x = new Position(0,1,1);
        assertTrue(s.win(x));
    }
    @Test
    void gyozelemTest_False() {
        x = new Position(2,0,1);
        assertFalse(s.win(x));
    }

    @Test
    void bflTest_NemMertSzelenVan() {
        x = new Position(7,0,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.balrafel(x, tabla));
    }
    @Test
    void bflTest_NemLepMertFentVan() {
        x = new Position(0,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.balrafel(x, tabla));
    }
    @Test
    void bflTest_IgenMertSzelenVan() {
        x = new Position(7,7,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.balrafel(x, tabla));
    }
    @Test
    void bflTest_IgenMertLentVan() {
        x = new Position(7,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.balrafel(x, tabla));
    }

    @Test
    void bllTest_NemMertLentVan() {
        x = new Position(7,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.balrale(x, tabla));
    }
    @Test
    void bllTest_NemMertSzelenVan() {
        x = new Position(0,0,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.balrale(x, tabla));
    }
    @Test
    void bllTest_IgenMertFentVan() {
        x = new Position(0,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.balrale(x, tabla));
    }
    @Test
    void bllTest_IgenMertSzelenVan() {
        x = new Position(0,7,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.balrale(x, tabla));
    }

    @Test
    void jflTest_NemMertFentVan() {
        x = new Position(0,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.jobbrafel(x, tabla));
    }
    @Test
    void jflTest_NemMertSzelenVan() {
        x = new Position(4,7,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.jobbrafel(x, tabla));
    }
    @Test
    void jflTest_IgenMertLentVan() {
        x = new Position(7,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.jobbrafel(x, tabla));
    }@Test
    void jflTest_IgenMertSzelenVan() {
        x = new Position(4,0,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.jobbrafel(x, tabla));
    }

    @Test
    void jllTest_NemMertLentVan() {
        x = new Position(7,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.jobbrale(x, tabla));
    }
    @Test
    void jllTest_NemMertSzelenVan() {
        x = new Position(4,7,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertFalse(s.jobbrale(x, tabla));
    }
    @Test
    void jllTest_IegnMertFentVan() {
        x = new Position(0,4,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.jobbrale(x, tabla));
    }
    @Test
    void jllTest_IgenMertSzelenVan() {
        x = new Position(4,0,1);
        int[][] tabla = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabla[i][j] = 0;
            }
        }
        assertTrue(s.jobbrale(x, tabla));
    }
}