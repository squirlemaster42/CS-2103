import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Class that implements a ball with a position and velocity.
 */
public class Ball {
    // Constants
    /**
     * The radius of the ball.
     */
    public static final int BALL_RADIUS = 8;
    /**
     * The initial velocity of the ball in the x direction.
     */
    public static final double INITIAL_VX = 1e-7;
    /**
     * The initial velocity of the ball in the y direction.
     */
    public static final double INITIAL_VY = 1e-7;
    /**
     * Tolerance value for detecting collision
     */
    public static final int COLLISION_TOLERANCE = 15;

    /**
     * Instance variables
     * (x,y) is the position of the center of the ball.
     */
    private double x, y;
    private double vx, vy;
    private Circle circle;
    private int bottomWallHits = 0;

    /**
     * @return the Circle object that represents the ball on the game board.
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Constructs a new Ball object at the centroid of the game board
     * with a default velocity that points down and right.
     */
    public Ball() {
        x = GameImpl.WIDTH / 2;
        y = GameImpl.HEIGHT / 2;
        vx = INITIAL_VX;
        vy = INITIAL_VY;

        circle = new Circle(BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
        circle.setLayoutX(x - BALL_RADIUS);
        circle.setLayoutY(y - BALL_RADIUS);
        circle.setFill(Color.BLACK);
    }

    /**
     * Updates the position of the ball, given its current position and velocity,
     * based on the specified elapsed time since the last update.
     *
     * @param deltaNanoTime the number of nanoseconds that have transpired since the last update
     */
    public void updatePosition(long deltaNanoTime) {
        double dx = vx * deltaNanoTime;
        double dy = vy * deltaNanoTime;

        //Checks if the ball is hitting the left or right of the screen
        if ((x - BALL_RADIUS + dx < 0 && vx < 0) || (x + BALL_RADIUS + dx > GameImpl.WIDTH && vx > 0)) {
            vx *= -1;
        }

        //Checks if the ball is hitting the top or bottom of the screen
        if ((y + BALL_RADIUS + dy > GameImpl.HEIGHT && vy > 0)) {
            vy *= -1;
            bottomWallHits++; //Increases the number of times the bottom wall has been hit
        } else if ((y - BALL_RADIUS + dy < 0 && vy < 0)) {
            vy *= -1;
        }

        //Updates the x and y coords
        x += dx;
        y += dy;

        //Moves the ball to the new x and y coords
        circle.setTranslateX(x - (circle.getLayoutX() + BALL_RADIUS));
        circle.setTranslateY(y - (circle.getLayoutY() + BALL_RADIUS));
    }

    /**
     * Checks if the ball is colliding with the paddle
     * @param paddleBounds The bounds of the paddle
     */
    void checkPaddleCollision(final Bounds paddleBounds) {
        //Check if the ball is contacting the paddle
        if (paddleBounds.intersects(circle.getBoundsInParent())) {
            System.out.println("Collision with paddle");
            vy *= -1; //Reverse the direction of the ball
        }
    }

    /**
     * Checks if the ball is colliding with with an animal and alters the direction
     * @param animals A List of animals to check
     */
    void checkAnimalCollision(final List<Animal> animals) {
        for (Animal animal : animals) {
            //Check is we are colliding with an animal
            //checks which side of the animal we collide with
            //increasing the velocity on every hit
            //changing the ball's direction based on which side of the animal the ball hits
            if (circle.getBoundsInParent().intersects(animal.getBounds())) {
                if (animal.getBounds().getMaxX() < (int) (x - BALL_RADIUS + COLLISION_TOLERANCE) && vx < 0) {
                    //checks for hitting right side of animal
                    System.out.println("hits on the right");
                    vx *= -1.2;
                    animal.deactivate();
                } else if (animal.getBounds().getMinX() > (int) (x + BALL_RADIUS - COLLISION_TOLERANCE) && vx > 0) {
                    //checks for hit on left side
                    System.out.println(" hits on left ");
                    vx *= -1.2;
                    animal.deactivate();
                }
                if (animal.getBounds().getMaxY() < (int)(y - BALL_RADIUS + COLLISION_TOLERANCE) && vy < 0) {
                    //checks for hits on bottom side
                    System.out.println("hits on the bottom");
                    vy *= -1.2;
                    animal.deactivate();
                } else if (animal.getBounds().getMinY() > (int)(y + BALL_RADIUS - COLLISION_TOLERANCE) && vy > 0) {
                    //checks for hit on top
                    System.out.println("hits on top");
                    vy *= -1.2;
                    animal.deactivate();
                }
            }
        }
    }

    /**
     * Gets the number of times the ball has hit the bottom wall
     * @return An int representing the number of times the ball has hit the bottom wall
     */
    int getBottomWallHits() {
        return bottomWallHits;
    }
}
