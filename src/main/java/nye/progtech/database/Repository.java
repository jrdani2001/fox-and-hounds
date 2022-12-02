package nye.progtech.database;

import java.sql.*;

/**
 * Jatekos adatbazis.
 */

public class Repository {
    private String nev;
    private int id;
    private int gyozelmekSzama = 0;
    private int veresegekSzama = 0;
    private int jatszottMeccsekSzama = 0;
    private static int lepesekSzama = 0;

    private int osszGyozelmek;
    private int osszVereseg;
    private int osszMeccsek;
    private int osszLepesek;

    private final Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Letrehozza a tablat, ha nem letezik.
     */
    public void createTableIfNotExists() throws SQLException {

        String command = "CREATE TABLE IF NOT EXISTS PLAYERDATABASE " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                " NEV VARCHAR(255), " +
                " MECCSEK INTEGER, " +
                " GYOZELMEK INTEGER, " +
                " VERESEGEK INTEGER, " +
                " LEPESEK INTEGER)";

        Statement st = connection.createStatement();
        st.executeUpdate(command);
    }

    /**
     * Frissiti az állást adatbázisból és kiirja.
     */
    public void osszStat() throws SQLException {
        System.out.println("Állapota: ");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM PLAYERDATABASE WHERE NEV = '" + nev + "'");
        while (rs.next()) {
            id = rs.getInt("ID");
            nev = rs.getString("NEV");
            osszMeccsek = rs.getInt("MECCSEK");
            osszGyozelmek = rs.getInt("GYOZELMEK");
            osszVereseg = rs.getInt("VERESEGEK");
            osszLepesek = rs.getInt("LEPESEK");

            System.out.println("Név: " + nev);
            System.out.println("Játszott meccsek száma: " + osszMeccsek);
            System.out.println("Győzelmek száma: " + osszGyozelmek);
            System.out.println("Vereségek száma: " + osszVereseg);
            System.out.println("Megtett lépések száma: " + osszLepesek);
        }
    }

    /**
     * Lekéri az eredményeket és utáni frissiti.
     */
    public void statUpdate() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM PLAYERDATABASE WHERE NEV = '" + nev + "'");
        while (rs.next()) {
            id = rs.getInt("ID");
            nev = rs.getString("NEV");
            osszMeccsek = rs.getInt("JATSZOTTMECCSEK");
            osszGyozelmek = rs.getInt("GYOZELMEK");
            osszVereseg = rs.getInt("VERESEGEK");
            osszLepesek = rs.getInt("LEPESEK");
        }

        String command = "UPDATE PLAYERDATABASE SET(JATSZOTTMECCSEK, GYOZELMEK, VERESEGEK, LEPESEK) = VALUES(?, ?, ?, ?) WHERE NEV = ?";
        PreparedStatement st = connection.prepareStatement(command);
        st.setInt(1, osszMeccsek + jatszottMeccsekSzama);
        st.setInt(2, osszGyozelmek + gyozelmekSzama);
        st.setInt(3, osszVereseg + veresegekSzama);
        st.setInt(4, osszLepesek + lepesekSzama);
        st.setString(5, nev);
        st.executeUpdate();
        lepesekSzama = 0;
        gyozelmekSzama = 0;
        veresegekSzama = 0;
        jatszottMeccsekSzama = 0;
    }

    /**
     * Ha a játékos nyer akkor megnöveljük a győzelmek számát.
     */
    public void win() {
        gyozelmekSzama++;
        jatszottMeccsekSzama++;
    }

    /**
     * Vereség esetén a vesztések számát növeljük.
     */
    public void lose() {
        veresegekSzama++;
        jatszottMeccsekSzama++;
    }

    /**
     * Kilistazza a jatekosokat.
     */
    public void scoreBoard() throws SQLException {
        String s = "SELECT NEV, GYOZELMEK FROM PLAYERDATABASE ORDER BY GYOZELMEK DESC";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(s);
        int i = 1;
        System.out.printf("%10s  |  %s\n", "Név", "Győzelmek");
        while (rs.next()) {
            String nev = rs.getString("NEV");
            int gyozelmek = rs.getInt("GYOZELMEK");
            System.out.printf("%d.: %7s | %d \n", i, nev, gyozelmek);
            i++;
        }
    }

    /**
     * Noveljuk a lepesek szamat.
     */
    public void megtettLepes() {
        lepesekSzama++;
    }

    /**
     * Lekerjuk a lepesek szamat.
     */
    public int getLepesekSzama() {
        return lepesekSzama;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getNev() {
        return nev;
    }
}