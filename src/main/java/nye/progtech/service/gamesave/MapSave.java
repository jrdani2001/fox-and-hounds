package nye.progtech.service.gamesave;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A pálya elmentése.
 */
@XmlRootElement
public class MapSave {
    int[][] tabla;

    public MapSave(int[][] tabla) {
        this.tabla = tabla;
    }

    public MapSave() {
    }

    public int[][] getTabla() {
        return tabla;
    }

    public void setTabla(int[][] tabla) {
        this.tabla = tabla;
    }
}
