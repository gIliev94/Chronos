package bc.bg.tools.chronos.endpoint.ui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class ChronosRoot extends Parent implements IChronosRoot {

    protected Pane container;

    public ChronosRoot() {
    }

    public ChronosRoot(Pane container) {
	this.container = container;
    }

    public boolean add(Node componentNode) {
	if (container != null) {
	    return container.getChildren().add(componentNode);
	}

	return false;
    }

    public Pane getContainer() {
	return this.container;
    }

    public void setContainer(Pane container) {
	this.container = container;
    }
}
