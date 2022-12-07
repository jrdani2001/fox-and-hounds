package nye.progtech.service.gamesave;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


/**
 * Mentés és betöltés osztálya.
 */
public class Save {

    /**
     * Játék állás mentése.
     *
     * @param tabla a tábla nevét menti.
     * @param nev a játékos nevét menti.
     * @throws JAXBException xml hibát dob.
     */
    public void save(MapSave tabla, String nev) throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(MapSave.class);
        Marshaller marshaller = jbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(tabla, new File("src//main//resources//" + nev + ".xml"));
    }


}
