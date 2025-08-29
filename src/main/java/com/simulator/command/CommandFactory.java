package com.simulator.command;

import com.simulator.controller.LoanController;
import com.simulator.view.LoanView;

public class CommandFactory {
    private LoanController controller;
    private LoanView view;

    public CommandFactory(LoanController controller, LoanView view) {
        this.controller = controller;
        this.view = view;
    }

    public Command create(String cmd) {
        switch (cmd.toLowerCase()) {
            case "new":
                return new CalculationCommand(controller);
            case "upload":
                return new UploadCommand(controller, view);
            case "load":
                return new LoadFromClientCommand(controller, view);
            case "show":
                return new ShowMenuCommand();
            default:
                return null;
        }
    }
}