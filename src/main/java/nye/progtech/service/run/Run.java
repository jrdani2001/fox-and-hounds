package nye.progtech.service.run;

import java.sql.SQLException;

import jakarta.xml.bind.JAXBException;
import nye.progtech.service.database.Repository;
import nye.progtech.service.menu.Menu;


/**
 * Játék futtatása.
 */
public class Run {
    Menu menu;
    Repository stat;

    /**
     * Futtatás.
     */
    public void runGame() throws SQLException, JAXBException {
        stat.createTableIfNotExists();
        menu.start();
        menu.menu();
    }

    public Run(Menu menu, Repository stat) {
        this.menu = menu;
        this.stat = stat;
    }
}
