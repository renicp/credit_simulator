package com.simulator.command;

import com.simulator.controller.Controller;
import com.simulator.view.View;

public class UploadCommand implements Command {
    private Controller controller;
    private View view;

    public UploadCommand(Controller controller, View view) {
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