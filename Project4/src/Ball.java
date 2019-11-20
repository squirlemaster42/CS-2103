import java.awt.*;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private double vx, vy;
	private Circle circle;
	private int bottomWallHits = 0;

	/**
	 * @return the Circle object that represents the ball on the game board.
	 */
	public Circle getCircle () {
		return circle;
	}

	/**
	 * Constructs a new Ball object at the centroid of the game board
	 * with a default velocity that points down and right.
	 */
	public Ball () {
		x = GameImpl.WIDTH/2;
		y = GameImpl.HEIGHT/2;
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
	 * @param deltaNanoTime the number of nanoseconds that have transpired since the last update
	 */
	public void updatePosition (long deltaNanoTime) {
		double dx = vx * deltaNanoTime;
		double dy = vy * deltaNanoTime;
		//TODO Cleanup
		//TODO Fix getting stuck
		if((x - BALL_RADIUS + dx < 0 && vx < 0) || (x + BALL_RADIUS + dx > GameImpl.WIDTH && vx > 0)){
			vx *= -1;
		}
		//TODO Split up to detect collision with bottom wall
		//TODO Store times collided with bottom wall
		if((y - BALL_RADIUS + dy < 0 && vy < 0) || (y + BALL_RADIUS + dy > GameImpl.HEIGHT && vy > 0)){
			vy *= -1;
		}
		x += dx;
		y += dy;

		circle.setTranslateX(x - (circle.getLayoutX() + BALL_RADIUS));
		circle.setTranslateY(y - (circle.getLayoutY() + BALL_RADIUS));
	}

	void checkPaddleCollision(final Bounds paddleBounds){
		if(paddleBounds.intersects(circle.getBoundsInParent())){
			System.out.println("Collision with paddle");
			vy *= -1;
		}
	}

	void checkAnimalCollision(final Animal[][] animals){
		for(Animal[] animalArr : animals){
			for(Animal animal : animalArr){
				//TODO Need to determine if we are hitting from the left, right, top, or bottom
				if(circle.getBoundsInParent().intersects(animal.getBounds())){
					System.out.println("Collision with animal");
					//TODO Fine tune speed increase
					vx *= -1.1;
					vy *= -1.1;
					animal.deactivate();
				}
			}
		}
	}

	int getBottomWallHits(){
		return bottomWallHits;
	}
}
