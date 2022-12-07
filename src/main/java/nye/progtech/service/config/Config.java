package nye.progtech.service.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nye.progtech.service.database.Player;
import nye.progtech.service.database.Repository;
import nye.progtech.service.game.Map;
import nye.progtech.service.menu.Menu;
import nye.progtech.service.menu.Run;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring.
 */
@Configuration
public class Config {

    Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

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
