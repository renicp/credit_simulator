package com.simulator.command;

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