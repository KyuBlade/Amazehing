package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.factory.entity.ProcessingFactory;

public class AddItemCommand extends Command {

    private ProcessingFactory processingFactory;

    public AddItemCommand(ProcessingFactory processingFactory) {
	super("addItem");

	this.processingFactory = processingFactory;

	addParameter(new Parameter("item", Integer.class, false));
	addParameter(new Parameter("quantity", Integer.class, true));
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	int _itemId = (Integer) args.get(0).getValue();
	int _quantity = 1;
	if (args.size > 1) {
	    _quantity = (Integer) args.get(1).getValue();
	}

	final int _finalQuantity = _quantity;
	// processingFactory.createInventoryAdd(_itemId, _quantity, new SuccesCallback() {
	//
	// @Override
	// public void done(boolean succes) {
	// if (succes) {
	// console.addEntry("Item" + ((_finalQuantity > 1) ? "s" : "") + " added");
	// } else {
	// console.addEntry("Unable to add item" + ((_finalQuantity > 1) ? "s" : ""));
	// }
	// }
	// });
    }
}