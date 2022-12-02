package nye.progtech.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nye.progtech.database.Player;
import nye.progtech.database.Repository;
import nye.progtech.game.Map;
import nye.progtech.menu.Menu;
import nye.progtech.menu.Run;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Springhez kellenek ezek a cuccok.
 */
@Configuration
public class Config {

    Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/PD", "sa", "pass");

    public Config() throws SQLException {
    }

    @Bean
    Menu menu() {
        return new Menu(stat(), player(), board());
    }

    @Bean(initMethod = "runGame")
    Run run() {
        return new Run(menu(), stat());
    }

    @Bean
    Repository stat() {
        return new Repository(connection);
    }

    @Bean
    Player player() {
        return new Player(connection, stat());
    }

    @Bean
    Map board() {
        return new Map();
    }
}
