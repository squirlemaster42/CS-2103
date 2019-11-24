import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animal {

    private double x, y;
    private boolean active;
    private final Bounds bounds;
    private final ImageView image;

    Animal(final Image image, final double initX, final double initY) {
        this.x = initX;
        this.y = initY;
        active = true;
        this.image = new ImageView(image);
        bounds = new BoundingBox(x, y, image.getWidth(), image.getHeight());
    }

    ImageView getImageView() {
        image.relocate(x, y);
        return image;
    }

    Bounds getBounds() {
        return bounds;
    }

    void deactivate() {
        active = false;
    }

    boolean isActive() {
        return active;
    }

    /**
     * Prints the x and y position of the animal
     * @return
     */
    @Override
    public String toString() {
        return "Animal{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
