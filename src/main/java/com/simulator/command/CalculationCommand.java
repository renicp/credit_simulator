package com.simulator.command;

import com.simulator.controller.LoanController;

public class CalculationCommand implements Command {
    private LoanController controller;

    public CalculationCommand(LoanController controller) {
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