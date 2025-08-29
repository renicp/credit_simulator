package com.simulator;

import java.util.Scanner;

import com.simulator.command.Command;
import com.simulator.command.CommandFactory;
import com.simulator.controller.LoanController;
import com.simulator.view.LoanView;

public class Main {
    public static void main(String[] args) {
        LoanView view = new LoanView();
        LoanController controller = new LoanController(view);
        CommandFactory factory = new CommandFactory(controller, view);

        Scanner sc = new Scanner(System.in);
        System.out.println("Ketik 'show' untuk lihat menu");

        while (true) {
            System.out.print("\n> ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            Command cmd = factory.create(input);
            if (cmd != null) {
                cmd.execute();
            } else {
                System.out.println("Perintah tidak dikenal. Ketik 'show' untuk menu.");
            }
        }

        System.out.println("Bye!");
    }
}