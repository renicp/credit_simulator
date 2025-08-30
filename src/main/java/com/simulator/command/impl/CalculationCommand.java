package com.simulator.command.impl;

import com.simulator.command.Command;
import com.simulator.controller.Controller;

public class CalculationCommand implements Command {
    private Controller controller;

    public CalculationCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.runInteractive();
    }

    @Override
    public String getName() {
        return "new";
    }

    @Override
    public String getDescription() {
        return "Buat perhitungan kredit baru";
    }
}