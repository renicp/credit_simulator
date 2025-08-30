package com.simulator.view.impl;

import java.util.List;
import java.util.Scanner;

import com.simulator.view.View;

public class LoanView implements View {
    private Scanner scanner = new Scanner(System.in);

    public String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void showResults(List<String> results) {
        for (String r : results) {
            System.out.println(r);
        }
    }

    public void showError(String message) {
        System.err.println("Error: " + message);
    }
}