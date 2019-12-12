import javafx.application.Application;

import java.awt.event.MouseListener;
import java.util.*;

import javafx.geometry.Bounds;
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

import javax.swing.plaf.synth.SynthOptionPaneUI;

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
        private final Pane expressionPane;

        MouseEventHandler(Pane pane_, CompoundExpression rootExpression_) {
            this.rootExpression = rootExpression_;
            this.expressionPane = pane_;
        }

        public void handle(MouseEvent event) {
            // Success! Add the expression's Node to the expressionPane

            // If the parsed expression is a CompoundExpression, then register some callbacks
            ((Pane) rootExpression.getNode()).setBorder(Expression.NO_BORDER);
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                handlePressed(event);
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                handleDragged(event);
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                handleReleased(event);
            }
        }

        //TODO Remove boarder once no longer focused
        private void handlePressed(MouseEvent mouseEvent) {
			changeFocus(mouseEvent);
			setColor(focus.getNode(), Expression.GHOST_COLOR);
            movingExpression = focus.deepCopy();
            ((AbstractExpression) movingExpression).calculateNode();
            expressionPane.getChildren().add(movingExpression.getNode());
        }

        private void handleDragged(MouseEvent mouseEvent) {
            movingExpression.getNode().setTranslateX(mouseEvent.getX() - movingExpression.getNode().getBoundsInLocal().getWidth()/2);
            movingExpression.getNode().setTranslateY(mouseEvent.getY() - movingExpression.getNode().getBoundsInLocal().getHeight()/2);
            ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
        }

        private void handleReleased(MouseEvent mouseEvent) {
            expressionPane.getChildren().remove(movingExpression.getNode());
            movingExpression = null;
            setColor(rootExpression.getNode(), Color.BLACK);
            ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
        }

        private void changeFocus(final MouseEvent e) {
			if(focus == null){
				focus = rootExpression;
			}else if(focus instanceof CompoundExpression){
				List<Expression> children = ((AbstractCompoundExpression)(focus)).getChildren();
				for (Expression ex : children){
					if(inNode(e,ex.getNode())){
						focus = ex;
						return;
					}
				}
			}else{
				List<Expression> children = ((AbstractCompoundExpression)(rootExpression)).getChildren();
				for (Expression ex : children){
					if(inNode(e,ex.getNode())){
						focus = ex;
						return;
					}
				}
			}
            ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
        }

        private void setColor(final Node n, final Color c) {
            if (n instanceof Label) {
                ((Label) n).setTextFill(c);
            } else {
                ((HBox) n).getChildren().forEach(e -> setColor(e, c));
            }
        }

        //TODO Change names
        private boolean inNode(final MouseEvent e, final Node n) {
            //TODO Check if we should use bounds instead
			Bounds boundsInScence = n.localToScene(n.getBoundsInLocal());
			return boundsInScence.contains(new Point2D(e.getSceneX(),e.getSceneY()));
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
        queryPane.getChildren().add(button);

        final Pane expressionPane = new Pane();

        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(e -> {
            //TODO Change to use MouseEventHandler class
            // Try to parse the expression
            //TODO Set focus
            try {
                // Success! Add the expression's Node to the expressionPane
                final Expression expression = expressionParser.parse(textField.getText(), true);
                ((AbstractExpression) expression).calculateNode();
                expressionPane.getChildren().clear();
                expressionPane.getChildren().add(expression.getNode());

                final EventHandler<MouseEvent> mouseHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                expressionPane.setOnMousePressed(mouseHandler);
                expressionPane.setOnMouseDragged(mouseHandler);
                expressionPane.setOnMouseReleased(mouseHandler);
			} catch (ExpressionParseException epe) {
                // If we can't parse the expression, then mark it in red
                textField.setStyle("-fx-text-fill: red");
            }
        });
        expressionPane.setTranslateY(EXPRESSION_POSY);
        expressionPane.setTranslateX(EXPRESSION_POSX);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    //takes node and  int
    //make method
}
