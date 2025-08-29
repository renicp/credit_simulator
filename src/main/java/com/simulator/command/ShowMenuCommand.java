package com.simulator.command;

public class ShowMenuCommand implements Command {
    @Override
    public void execute() {
        System.out.println("=== MENU ===");
        System.out.println("new   - Buat perhitungan kredit baru");
        System.out.println("upload- Load perhitungan dari file input");
        System.out.println("load  - Load perhitungan dari client");
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