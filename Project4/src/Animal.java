import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animal {

    /**
     * X and y coordinates of the image
     */
    private double x, y;
    /**
     * True if the animal is being displayed false otherwise
     */
    private boolean active;
    /**
     * Bounds of the image around the animal
     */
    private final Bounds bounds;
    /**
     * Displays the image
     */
    private final ImageView image;

    /**
     * Creates an instance of Animal
     * @param image Image of the animal
     * @param initX X coordinate of the topleft of the image
     * @param initY Y Coordinate of the topLeft of the image
     */
    Animal(final Image image, final double initX, final double initY) {
        this.x = initX;
        this.y = initY;
        active = true;
        this.image = new ImageView(image);
        bounds = new BoundingBox(x, y, image.getWidth(), image.getHeight());
    }

    /**
     * Gets the imageView instance variable
     * @return the imageview
     */
    ImageView getImageView() {
        image.relocate(x, y);
        return image;
    }

    /**
     * Gets the bounds instance variable
     * @return the bounds
     */
    Bounds getBounds() {
        return bounds;
    }

    /**
     * sets activate to false
     */
    void deactivate() {
        active = false;
    }

    /**
     * Returns the state of active
     * @return true if active is true faslse if it is false
     */
    boolean isActive() {
        return active;
    }

    /**
     * Prints the x and y position of the animal
     * @return A string containing the x and y position of the animal
     */
    @Override
    public String toString() {
        return "Animal{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
