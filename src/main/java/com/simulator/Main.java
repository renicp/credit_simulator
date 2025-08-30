package com.simulator;

import java.util.Scanner;

import com.simulator.api.GetDataLoan;
import com.simulator.api.HttpClient;
import com.simulator.command.Command;
import com.simulator.command.CommandFactory;
import com.simulator.controller.impl.LoanController;
import com.simulator.view.View;
import com.simulator.view.impl.LoanView;

public class Main {
    public static void main(String[] args) {
        View view = new LoanView();
        HttpClient httpClient = new GetDataLoan();
        LoanController controller = new LoanController(view, httpClient);

        if (args.length > 0) {
            String filename = args[0];
            System.out.println("Membaca input dari file: " + filename);
            controller.runWithFile(filename); 
            System.out.println("\nProses dari file selesai.");

        } else {
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
        }

        System.out.println("Bye!");
    }
}
