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
    private static boolean shouldMove = false;

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {
        private final CompoundExpression rootExpression;
        private final Pane expressionPane;

        MouseEventHandler(Pane pane_, CompoundExpression rootExpression_) {
            this.rootExpression = rootExpression_;
            this.expressionPane = pane_;
            changeFocus(null);
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
            if(shouldMove){
                movingExpression.getNode().setTranslateX(mouseEvent.getX() - (focus.getNode().getBoundsInLocal().getWidth() + 40));
                movingExpression.getNode().setTranslateY(mouseEvent.getY() - (focus.getNode().getBoundsInLocal().getHeight() + 40));
                ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
            }
        }

        private void handleDragged(MouseEvent mouseEvent) {
            if(shouldMove){
                movingExpression.getNode().setTranslateX(mouseEvent.getX() - (focus.getNode().getBoundsInLocal().getWidth() + 40));
                movingExpression.getNode().setTranslateY(mouseEvent.getY() - (focus.getNode().getBoundsInLocal().getHeight() + 40));

                //Handle Swap
               // ((AbstractCompoundExpression) focus.getParent()).setChildren(checkSwap(focus, mouseEvent));

                ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
            }
        }

        private void handleReleased(MouseEvent mouseEvent) {
            if(shouldMove){
                expressionPane.getChildren().remove(movingExpression.getNode());
                movingExpression = null;
                setColor(rootExpression.getNode(), Color.BLACK);
                ((Pane) focus.getNode()).setBorder(Expression.RED_BORDER);
            }
        }

        private void changeFocus(final MouseEvent e) {
            if(focus != null){
                ((Pane) focus.getNode()).setBorder(Expression.NO_BORDER);
            }
			if(focus == null){
				focus = rootExpression;
                ((Pane) focus.getNode()).setBorder(Expression.NO_BORDER);
				return;
			}else if(focus instanceof CompoundExpression){
				List<Expression> children = ((AbstractCompoundExpression)(focus)).getChildren();
				for (Expression ex : children){
					if(inNode(e,ex.getNode())){
						focus = ex;
						shouldMove = true;
						return;
					}
				}
			}
            List<Expression> children = ((AbstractCompoundExpression)(rootExpression)).getChildren();
            for (Expression ex : children){
                if(inNode(e,ex.getNode())){
                    focus = ex;
                    shouldMove = true;
                    return;
                }
            }
            focus = rootExpression;
            shouldMove = false;
            ((Pane) focus.getNode()).setBorder(Expression.NO_BORDER);
        }

        private List<Expression> checkSwap(final Expression exp, final MouseEvent mouseEvent){
            //Check data structure
            final List<List<Expression>> possibleOrders = new ArrayList<>();
            final List<Expression> parentList = ((AbstractCompoundExpression) exp.getParent()).getChildren();
            parentList.remove(exp);
            for(int i = 0; i <= parentList.size(); i++){
                final List<Expression> newList = new ArrayList<>(parentList);
                newList.add(i, exp);
                possibleOrders.add(newList);
            }
            double min = Integer.MAX_VALUE;
            List<Expression> closest = null;
            for(List<Expression> expressions : possibleOrders){
                System.out.println(Arrays.deepToString(new List[]{expressions}));
                //Get the difference between where we are ideally and where we are
                //Check if we are less than the min
                //Set closest
                ((AbstractCompoundExpression) exp.getParent()).setChildren(expressions);
                //final double movingCenterX = movingExpression.getNode().getBoundsInParent().getCenterX();
                Expression targetExp = null;
                for(Expression expression : expressions){
                    if(expression.equals(exp)){
                        Expression copy = exp.deepCopy();
                        ((AbstractExpression) copy).calculateNode();
                        targetExp = copy;
                        break;
                    }
                }
                List<Expression> expCopy = new ArrayList<>();
                for(Expression e : expressions){
                    if(e.equals(exp)){
                        expCopy.add(targetExp);
                    }else{
                        expCopy.add(e.deepCopy());
                    }
                }
                expCopy.forEach(e -> ((AbstractExpression) e).calculateNode());
                //TODO Recalc
               /* final double targetCenterX = targetExp.getNode().getBoundsInParent().getCenterX();
                final double delta = Math.abs(targetCenterX - movingCenterX); //TODO Check abs
                System.out.println("Delta: " + delta);
                if(delta < min){
                    min = delta;
                    closest = expressions;
                }*/
            }
            return closest;
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
			Bounds boundsInScene = n.localToScene(n.getBoundsInLocal());
			return boundsInScene.contains(new Point2D(e.getSceneX(),e.getSceneY()));
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
        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        button.setOnMouseClicked(e -> {
            //TODO Change to use MouseEventHandler class
            // Try to parse the expression
            //TODO Set focus
            try {
                // Success! Add the expression's Node to the expressionPane
                focus = null;
                final Expression expression = expressionParser.parse(textField.getText(), true);
                ((AbstractExpression) expression).calculateNode();
                expressionPane.getChildren().clear();
                expressionPane.getChildren().add(expression.getNode());

                final EventHandler<MouseEvent> mouseHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                root.setOnMousePressed(mouseHandler);
                root.setOnMouseDragged(mouseHandler);
                root.setOnMouseReleased(mouseHandler);
			} catch (ExpressionParseException epe) {
                // If we can't parse the expression, then mark it in red
                textField.setStyle("-fx-text-fill: red");
            }
        });
        expressionPane.setTranslateY(EXPRESSION_POSY);
        expressionPane.setTranslateX(EXPRESSION_POSX);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    //takes node and  int
    //make method
}
