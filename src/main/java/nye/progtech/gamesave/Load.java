package nye.progtech.gamesave;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import nye.progtech.game.Map;

import java.io.File;

/**
 * Mentett áláás betöltése.
 */
public class Load {
    /**
     * Mentett állás betöltése.
     *
     * @param nev betölti a nevet.
     * @param map és a mapot.
     * @throws JAXBException xml hibát dob ha nem sikerul.
     */
    public static void load(String nev, Map map) throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(MapSave.class);
        Unmarshaller unmarshaller = jbc.createUnmarshaller();
        MapSave readTabla = (MapSave)
                unmarshaller.unmarshal(new File("src//main//resources//" + nev + ".xml"));
        map.setMap(readTabla.getTabla());
    }
}
