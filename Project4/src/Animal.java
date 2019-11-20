import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animal {

    private double x, y;
    private boolean active;
    private final Bounds bounds;
    private final Image image;

    Animal(final Image image, final double initX, final double initY){
        this.x = initX;
        this.y = initY;
        active = true;
        this.image = image;
        bounds = new BoundingBox(x, y, image.getWidth(), image.getHeight());
    }

    ImageView getImageView(){
        if(active){
            final ImageView imageView = new ImageView(this.image);
            imageView.relocate(x, y);
            return imageView;
        }else{
            return null;
        }
    }

    Bounds getBounds(){
        return bounds;
    }

    void deactivate(){
        active = false;
    }

    boolean isActive(){
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
