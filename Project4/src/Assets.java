import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Assets {

    static Image HORSE, DUCK, GOAT;

    /**
     * Loads the images that will represent the animals
     * @throws FileNotFoundException
     */
    static void init() throws FileNotFoundException {
        HORSE = new Image(new FileInputStream("horse.jpg"));
        DUCK = new Image(new FileInputStream("duck.jpg"));
        GOAT = new Image(new FileInputStream("goat.jpg"));
    }
}
