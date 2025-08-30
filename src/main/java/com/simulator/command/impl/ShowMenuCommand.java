package com.simulator.command.impl;

import com.simulator.command.Command;

public class ShowMenuCommand implements Command {
    @Override
    public void execute() {
        System.out.println("=== MENU ===");
        System.out.println("new   - Buat perhitungan kredit baru");
        System.out.println("load  - Load perhitungan dari api external");
        System.out.println("show  - Tampilkan menu ini");
        System.out.println("exit  - Keluar aplikasi");
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Tampilkan menu";
    }
}