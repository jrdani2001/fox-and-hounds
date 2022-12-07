package nye.progtech;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A program belépési pontja.
 */

public class Main {

    /**
     * Futtatás.
     */

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("nye.progtech.config");
    }
}