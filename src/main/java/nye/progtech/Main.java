package nye.progtech;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A program belépési pontja.
 */
public class Main {
    /**
     * Futtatás.
     */
    public static void main(String[] args) {

        new AnnotationConfigApplicationContext("nye.progtech.configuration");
    }
}