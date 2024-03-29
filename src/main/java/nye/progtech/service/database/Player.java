package nye.progtech.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Letrehozza, megkeresi a jatekost.
 */
public class Player {

    private String nev;

    private final Connection connection;
    private final Repository repository;

    public Player(Connection connection, Repository repository) {
        this.connection = connection;
        this.repository = repository;
    }

    /**
     * Bekeri a neved.
     */
    public void logIn(Scanner scanner) {
        System.out.print("Írd be a neved: ");
        nev = scanner.next().toUpperCase();
        repository.setNev(nev);
    }

    /**
     * Megnézi hogy a játékos létezik e.
     */
    public boolean findPlayer() throws SQLException {
        String find = "SELECT NEV FROM PLAYERDATABASE";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(find);
        String name;
        int i = 0;
        while (resultSet.next()) {
            name = resultSet.getString("NEV");
            if (name.equals(nev)) {
                i++;
            }
        }
        return i != 0;
    }

    /**
     * Ha nem létezik létrehoz egyet.
     */
    public void newPlayer() throws SQLException {
        String insert = "INSERT INTO PLAYERDATABASE (NEV, JATSZOTTMECCSEK, GYOZELMEK, VERESEGEK, LEPESEK) VALUES(?, 0, 0, 0, 0)";
        PreparedStatement st = connection.prepareStatement(insert);
        st.setString(1, nev);
        st.executeUpdate();
        System.out.println("Nem létezik mentésed, létrehozásra került egy új");
    }
}
