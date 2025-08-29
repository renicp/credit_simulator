package com.simulator.command;

import com.simulator.controller.LoanController;
import com.simulator.view.LoanView;

public class LoadFromClientCommand implements Command {
    private LoanController controller;
    private LoanView view;

    public LoadFromClientCommand(LoanController controller, LoanView view) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void execute() {
        controller.loadFromServer();
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getDescription() {
        return "Load perhitungan dari client";
    }
}