import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animal {

    private double x, y;
    //True if the image should be displayed on the screen
    private boolean active;
    private final Bounds bounds;
    private final ImageView image;

    /**
     * Creates an animal
     * @param image The image for the animal
     * @param initX The initial x position of the animal
     * @param initY The initial y position of the animal
     */
    Animal(final Image image, final double initX, final double initY) {
        this.x = initX;
        this.y = initY;
        active = true;
        this.image = new ImageView(image);

        //Creates a bounding box that encloses the image of the image
        bounds = new BoundingBox(x, y, image.getWidth(), image.getHeight());
    }

    /**
     * An ImageView located at the position of the animal
     * @return ImageView located at the animal's position
     */
    ImageView getImageView() {
        image.relocate(x, y);
        return image;
    }

    /**
     * Gets the bounds of the animal
     * @return The bounding box for the animal
     */
    Bounds getBounds() {
        return bounds;
    }

    /**
     * Deactivates the animal (it should no longer be displayed to the screen)
     */
    void deactivate() {
        active = false;
    }

    /**
     * Determines if the animal is active
     * @return Returns true if the animal should be displayed to the screen, false otherwise
     */
    boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
