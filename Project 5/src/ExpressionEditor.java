import javafx.application.Application;

import java.awt.event.MouseListener;
import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

public class ExpressionEditor extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private final int EXPRESSION_POSY = WINDOW_HEIGHT / 4;
    private final int EXPRESSION_POSX = WINDOW_WIDTH / 8;

    private static Expression focus;
    private static Expression movingExpression;

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {
        private final CompoundExpression rootExpression;
        private final Pane pane;

        MouseEventHandler(Pane pane_, CompoundExpression rootExpression_) {
            this.rootExpression = rootExpression_;
            this.pane = pane_;
        }

        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            	handlePressed(event);
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				handleDragged(event);
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
				handleReleased(event);
            }
        }

        private void handlePressed(MouseEvent e) {

        }

        private void handleDragged(MouseEvent e) {

        }

        private void handleReleased(MouseEvent e) {

        }

        private void changeFocus(final MouseEvent e) {

        }

        private void changeColor(final Node n, final Color c) {
            if (n instanceof Label) {
                ((Label) n).setTextFill(c);
            } else {
                ((HBox) n).getChildren().forEach(e -> changeColor(e, c));
            }
        }

        private boolean inNode(final MouseEvent e, final Node n) {
            //TODO Check if we should use bounds instead
            return n.contains(new Point2D(e.getX(), e.getY()));
        }
    }

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;

    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";

    /**
     * Parser used for parsing expressions.
     */
    private final ExpressionParser expressionParser = new SimpleExpressionParser();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");

        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        final Button button = new Button("Parse");
        queryPane.getChildren().add(textField);

        final Pane expressionPane = new Pane();

        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                //TODO Change to use MouseEventHandler class
                // Try to parse the expression
                //TODO Set focus
                try {
                    // Success! Add the expression's Node to the expressionPane
                    final Expression expression = expressionParser.parse(textField.getText(), true);
                    expressionPane.getChildren().clear();
                    expressionPane.getChildren().add(expression.getNode());
                    expression.getNode().setLayoutX(WINDOW_WIDTH / 4);
                    expression.getNode().setLayoutY(WINDOW_HEIGHT / 2);

                    // If the parsed expression is a CompoundExpression, then register some callbacks
                    if (expression instanceof CompoundExpression) {
                        //TODO Figure out why it moves on first click
                        ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                        expressionPane.setOnMousePressed(mouseEvent -> {
                            //TODO Set color
                            movingExpression = expression.deepCopy();
                            queryPane.getChildren().add(movingExpression.getNode());
                        });
                        expressionPane.setOnMouseDragged(mouseEvent -> {
                            //TODO Make it move again
                            movingExpression.getNode().setTranslateX(mouseEvent.getX());
                            movingExpression.getNode().setTranslateY(mouseEvent.getY());
                        });
                        expressionPane.setOnMouseReleased(mouseEvent -> {
                            queryPane.getChildren().remove(movingExpression.getNode());
                            movingExpression = null;
                        });
                    }
                } catch (ExpressionParseException epe) {
                    // If we can't parse the expression, then mark it in red
                    textField.setStyle("-fx-text-fill: red");
                }
            }
        });
        expressionPane.setTranslateY(EXPRESSION_POSY);
        expressionPane.setTranslateX(EXPRESSION_POSX);

        queryPane.getChildren().add(button);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }
}
