package com.simulator.command;

import com.simulator.command.impl.CalculationCommand;
import com.simulator.command.impl.LoadFromExternalApiCommand;
import com.simulator.command.impl.ShowMenuCommand;
import com.simulator.controller.Controller;
import com.simulator.view.View;

public class CommandFactory {
    private Controller controller;
    private View view;

    public CommandFactory(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
    }

    public Command create(String cmd) {
        switch (cmd.toLowerCase()) {
            case "new":
                return new CalculationCommand(controller);
            case "show":
                return new ShowMenuCommand();
            case "load":
                return new LoadFromExternalApiCommand(controller, view);
            default:
                return null;
        }
    }
}