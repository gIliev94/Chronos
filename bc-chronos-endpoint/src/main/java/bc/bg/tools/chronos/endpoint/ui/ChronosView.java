package bc.bg.tools.chronos.endpoint.ui;

import javafx.scene.Scene;

public abstract class ChronosView extends Scene implements IChronosView {

    public ChronosView(ChronosRoot root, double width, double height) {
	super(root.getContainer(), width, height);
    }
}
