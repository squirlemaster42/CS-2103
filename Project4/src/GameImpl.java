import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.*;

public class GameImpl extends Pane implements Game {
    /**
     * The width of the game board.
     */
    public static final int WIDTH = 400;

    // Constants
    /**
     * The height of the game board.
     */
    public static final int HEIGHT = 600;
    /**
     * The number of times the ball has to hit the bottom wall to lose
     */
    public static final int LOSS_HITS = 5;
    /**s
     * The space to leave before drawing the images
     */
    public static final int X_PADDING = 50;
    public static final int Y_PADDING = 10;
    /**
     * The distance between each image
     */
    public static final int DIST_BETWEEN_IMAGE = 80;

    // Instance variables
    private Ball ball;
    private Paddle paddle;
    private Random rng;
    private final List<Animal> animals;

    /**
     * Constructs a new GameImpl.
     */
    public GameImpl() {
        setStyle("-fx-background-color: white;");

        try {
            Assets.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rng = new Random();
        animals = new ArrayList<>();

        restartGame(GameState.NEW);
    }

    public String getName() {
        return "Zutopia";
    }

    public Pane getPane() {
        return this;
    }

    private void
    restartGame(GameState state) {
        getChildren().clear();  // remove all components from the game

        // Create and add ball
        ball = new Ball();
        getChildren().add(ball.getCircle());  // Add the ball to the game board

        // Create and add animals ...
        //Creates a list of assets to use for animal images
        Image[] assets = new Image[]{Assets.HORSE, Assets.DUCK, Assets.GOAT};
        //Creates a list with 16 animals
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Chooses a random image for the animal and gives it coords
                final int xCoord = X_PADDING + DIST_BETWEEN_IMAGE * j;
                final int yCoord = Y_PADDING + DIST_BETWEEN_IMAGE * i;
                Animal animal = new Animal(assets[rng.nextInt(assets.length)], xCoord, yCoord);
                //Adds the new animal to the list of animals
                animals.add(animal);
                //Adds the image of the animal to the GUI
                getChildren().add(animal.getImageView());
            }
        }

        // Create and add paddle
        paddle = new Paddle();
        getChildren().add(paddle.getRectangle());  // Add the paddle to the game board

        // Add start message
        final String message;
        if (state == GameState.LOST) {
            message = "Game Over\n";
        } else if (state == GameState.WON) {
            message = "You won!\n";
        } else {
            message = "";
        }
        final Label startLabel = new Label(message + "Click mouse to start");
        startLabel.setLayoutX(WIDTH / 2 - 50);
        startLabel.setLayoutY(HEIGHT / 2 + 100);
        getChildren().add(startLabel);

        // Add event handler to start the game
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                GameImpl.this.setOnMouseClicked(null);

                // As soon as the mouse is clicked, remove the startLabel from the game board
                getChildren().remove(startLabel);
                run();
            }
        });

        // Add another event handler to steer paddle...
        setOnMouseMoved(e -> {
            paddle.moveTo(e.getX(), e.getY());
        });
    }

    /**
     * Begins the game-play by creating and starting an AnimationTimer.
     */
    public void run() {
        // Instantiate and start an AnimationTimer to update the component of the game.
        new AnimationTimer() {
            private long lastNanoTime = -1;

            public void handle(long currentNanoTime) {
                if (lastNanoTime >= 0) {  // Necessary for first clock-tick.
                    GameState state;
                    if ((state = runOneTimestep(currentNanoTime - lastNanoTime)) != GameState.ACTIVE) {
                        // Once the game is no longer ACTIVE, stop the AnimationTimer.
                        stop();
                        // Restart the game, with a message that depends on whether
                        // the user won or lost the game.
                        //checks if ball has hit bottom wall 5 times, resulting in a loss
                        restartGame(state);
                        if (checkForLoss(ball)) {
                            //sets the gamestate to LOST indicating game over
                            state = GameState.LOST;
                        }
                    }
                }
                // Keep track of how much time actually transpired since the last clock-tick.
                lastNanoTime = currentNanoTime;
            }
        }.start();
    }

    /**
     * Updates the state of the game at each timestep. In particular, this method should
     * move the ball, check if the ball collided with any of the animals, walls, or the paddle, etc.
     *
     * @param deltaNanoTime how much time (in nanoseconds) has transpired since the last update
     * @return the current game state
     */
    public GameState runOneTimestep(long deltaNanoTime) {
        //Checks if the ball is colliding with the paddle
        ball.checkPaddleCollision(paddle.getRectangle().getBoundsInParent());
        //Updates the position of the ball
        ball.updatePosition(deltaNanoTime);
        //Checks if the ball is colliding with any animals
        ball.checkAnimalCollision(animals);
        try{
            //Removes animals that are no longer active
			animals.forEach(animal -> {
				if (!animal.isActive()) {
					getChildren().remove(animal.getImageView());
					getChildren().remove(animal.getImageView());
					animals.remove(animal);
				}
			});
		}catch (ConcurrentModificationException e){
			System.out.print("");
		}
        //Updates the GameState if needed
        if (checkForLoss(ball)) {
            return GameState.LOST;
        } else if (animals.size() <= 0) {
            return GameState.WON;
        }
        return GameState.ACTIVE;
    }

    /**
     * Checks if the game should be lost
     * @param b The ball to check for losses
     * @return Returns true if the game should be lost, false otherwise
     */
    private boolean checkForLoss(Ball b) {
        return b.getBottomWallHits() == LOSS_HITS;
    }

    /**
     * Defines different states of the game.
     */
    public enum GameState {
        WON, LOST, ACTIVE, NEW
    }
}
