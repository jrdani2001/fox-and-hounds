package nye.progtech.menu;

import java.sql.SQLException;

import jakarta.xml.bind.JAXBException;
import nye.progtech.database.Repository;


/**
 * Játék futtatása.
 */
public class Run {
    Menu menu;
    Repository stat;

    public Run(Menu menu, Repository stat) {
        this.menu = menu;
        this.stat = stat;
    }

    /**
     * Futtatás.
     */
    public void runGame() throws SQLException, JAXBException {
        stat.createTableIfNotExists();
        menu.start();
        menu.menu();
    }
}
