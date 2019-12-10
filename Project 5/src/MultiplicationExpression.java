import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MultiplicationExpression extends AbstractCompoundExpression {

    /**
     * Creates an instance of Multiplication Expression
     * @param children children of the additive expression
     * @param parent parent of the additive expression
     */
    MultiplicationExpression(final List<Expression> children, final CompoundExpression parent){
        super(children, parent, "*");
    }

    /**
     * Creates a deep copy of the expression
     * @return the deep copy that was created
     */
    @Override
    public Expression deepCopy() {
        //Create a new instance of the children array
        List<Expression> newArr = new ArrayList<>();
        //Add deep copies of the children to the new array
        super.getChildren().forEach(e -> {
            Expression newE = e.deepCopy();
            newE.setParent(this);
            newArr.add(newE);
        });
        //Return a new instance of MultiplicationExpression
        return new MultiplicationExpression(newArr, null);
    }

    /**
     * Combines common symbols to simplify the tree
     */
    @Override
    public void flatten() {
        //If we are at the head of the tree we cannot be flattened
        //So we just proceed to flattening children
        if(parent == null){
            //Flatten the children
            for(Expression child : super.getChildren()){
                child.flatten();
            }
        }
        ListIterator<Expression> iter = super.getChildren().listIterator();
        while (iter.hasNext()){
            Expression child = iter.next();
            //Flatten the current child
            child.flatten();
            //Check if the child is an instance of MultiplicationExpression
            if(child instanceof MultiplicationExpression){
                //Remove the child from children
                iter.remove();
                //Set the parents of the child to our parent
                ((MultiplicationExpression) child).getChildren().forEach(e -> e.setParent(this));
                //Add the children to our children
                ((MultiplicationExpression) child).getChildren().forEach(iter::add);
            }
        }
    }

    @Override
    public Node getNode() {
        final HBox hBox = new HBox();

        for(int i = 0 ; i < super.getChildren().size(); i++){
            if(i == super.getChildren().size() - 1){
                hBox.getChildren().add(super.getChildren().get(i).getNode());
            }
            else{
                hBox.getChildren().add(super.getChildren().get(i).getNode());
                final Label times = new Label("*");
                times.setFont(_FONT);
                hBox.getChildren().add(times);
            }
        }

        return hBox;
    }
}
