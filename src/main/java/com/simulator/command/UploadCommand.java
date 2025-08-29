package com.simulator.command;

import com.simulator.controller.LoanController;
import com.simulator.view.LoanView;

public class UploadCommand implements Command {
    private LoanController controller;
    private LoanView view;

    public UploadCommand(LoanController controller, LoanView view) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void execute() {
        String filename = view.ask("Masukkan nama file input: ");
        controller.runWithFile(filename);
    }

    @Override
    public String getName() {
        return "upload";
    }

    @Override
    public String getDescription() {
        return "Load perhitungan dari file input";
    }
}