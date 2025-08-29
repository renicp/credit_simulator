package com.simulator.command;

import com.simulator.controller.Controller;
import com.simulator.view.View;

public class LoadFromExternalApiCommand implements Command {
    private Controller controller;
    private View view;

    public LoadFromExternalApiCommand(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void execute() {
        controller.loadFromExternalApi();
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