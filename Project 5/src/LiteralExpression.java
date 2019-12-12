import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class LiteralExpression extends AbstractExpression implements Expression{

    private final String value;

    private Node node = null;
    /**
     * Creates an Literal of Additive Expression
     * @param parent parent of the additive expression
     */
    LiteralExpression (final String value, final CompoundExpression parent){
        super(parent, value);
        this.value = value;
    }

    /**
     * Creates a deep copy of the expression
     * @return the deep copy that was created
     */
    @Override
    public Expression deepCopy() {
        return new LiteralExpression(value, null);
    }

    /**
     * Combines common symbols to simplify the tree
     */
    @Override
    public void flatten() {
        //Literal expressions are not flattened
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @Override
    public void calculateNode() {
        final HBox hBox = new HBox();
        final Label label = new Label(value);
        label.setFont(_FONT);
        hBox.getChildren().add(label);
        this.node = hBox;
    }

    @Override
    public String getSymbol() {
        return "";
    }

}
