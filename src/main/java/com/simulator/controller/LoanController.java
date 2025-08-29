package com.simulator.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.simulator.model.Loan;
import com.simulator.model.Vehicle;
import com.simulator.model.VehicleFactory;
import com.simulator.model.ApiResponse;
import com.simulator.service.LoanCalculator;
import com.simulator.view.LoanView;
import com.simulator.client.ClientMock;
import com.google.gson.Gson;

public class LoanController {
    private LoanView view;
    private ClientMock httpMethod; 

    public LoanController(LoanView view) {
        this.view = view;
        this.httpMethod = new ClientMock();
    }

    public void runInteractive() {
        try {
            // Jenis kendaraan
            String vehicleType = askLoop(
                    "Input Jenis Kendaraan (Mobil/Motor): ",
                    (val) -> val.equalsIgnoreCase("mobil") || val.equalsIgnoreCase("motor"),
                    "Jenis kendaraan harus 'Mobil' atau 'Motor'"
            );

            // Kondisi
            String vehicleCondition = askLoop(
                    "Input Kondisi (Baru/Bekas): ",
                    (val) -> val.equalsIgnoreCase("baru") || val.equalsIgnoreCase("bekas"),
                    "Kondisi harus 'Baru' atau 'Bekas'"
            );

            // Tahun kendaraan
            int year;
            while (true) {
                try {
                    String yearStr = askLoop(
                            "Input Tahun Kendaraan (yyyy): ",
                            (val) -> val.matches("\\d{4}"),
                            "Tahun harus 4 digit angka"
                    );
                    year = Integer.parseInt(yearStr);

                    int currentYear = java.time.Year.now().getValue();
                    if (vehicleCondition.equalsIgnoreCase("baru") && year < currentYear - 1) {
                        view.showError("Kendaraan BARU tidak boleh lebih lama dari " + (currentYear - 1));
                    } else if (vehicleCondition.equalsIgnoreCase("bekas") && year > currentYear) {
                        view.showError("Kendaraan BEKAS tidak boleh di masa depan (maksimal " + currentYear + ")");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    view.showError("Tahun tidak valid");
                }
            }

            // Jumlah pinjaman
            long totalLoan = Long.parseLong(askLoop(
                    "Input Jumlah Pinjaman (<= 1 M): ",
                    (val) -> val.matches("\\d+") && Long.parseLong(val) <= 1_000_000_000L,
                    "Jumlah pinjaman harus angka <= 1 milyar"
            ));

            // Tenor
            int tenor = Integer.parseInt(askLoop(
                    "Input Tenor Pinjaman (1-6): ",
                    (val) -> val.matches("\\d+") && Integer.parseInt(val) >= 1 && Integer.parseInt(val) <= 6,
                    "Tenor harus 1-6 tahun"
            ));

            // DP 
            Vehicle vehicle = VehicleFactory.create(vehicleType, vehicleCondition);
            long downPayment;
            while (true) {
                try {
                    downPayment = Long.parseLong(view.ask("Input DP: ").trim());
                    validateDP(vehicleCondition, totalLoan, downPayment);
                    break;
                } catch (Exception e) {
                    view.showError(e.getMessage());
                }
            }

            Loan loan = new Loan(vehicle, year, totalLoan, tenor, downPayment);

            // Hitung
            LoanCalculator calc = new LoanCalculator();
            List<String> results = calc.calculateInstallments(loan);
            view.showResults(results);

        } catch (Exception e) {
            view.showError("Error umum: " + e.getMessage());
        }
    }

    public void runWithFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) lines.add(line);

            String vehicleType = lines.get(0).trim();
            String vehicleCondition = lines.get(1).trim();
            int year = Integer.parseInt(lines.get(2).trim());
            long totalLoan = Long.parseLong(lines.get(3).trim());
            int tenor = Integer.parseInt(lines.get(4).trim());
            long dp = Long.parseLong(lines.get(5).trim());

            Vehicle vehicle = VehicleFactory.create(vehicleType, vehicleCondition);

            validateDP(vehicleCondition, totalLoan, dp);

            Loan loan = new Loan(vehicle, year, totalLoan, tenor, dp);

            LoanCalculator calc = new LoanCalculator();
            List<String> results = calc.calculateInstallments(loan);
            view.showResults(results);
        } catch (Exception e) {
            view.showError("File error: " + e.getMessage());
        }
    }

    public void loadFromServer() {
        try {
            String response = this.httpMethod.GETRequest();
            if (!response.equalsIgnoreCase("ERROR")) {
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

                Vehicle vehicle = VehicleFactory.create(
                        apiResponse.getVehicleType(),
                        apiResponse.getVehicleCondition()
                );

                validateDP(apiResponse.getVehicleCondition(),
                        apiResponse.getTotalLoanAmount(),
                        apiResponse.getDownPayment());

                Loan loan = new Loan(
                        vehicle,
                        apiResponse.getVehicleYear(),
                        apiResponse.getTotalLoanAmount(),
                        apiResponse.getLoanTenure(),
                        apiResponse.getDownPayment()
                );

                LoanCalculator calc = new LoanCalculator();
                List<String> results = calc.calculateInstallments(loan);
                view.showResults(results);
            } else {
                this.view.showError("Load Data From Server ERROR!!!");
            }
        } catch (Exception e) {
            this.view.showError("Load Data From Server Crash! " + e.getMessage());
        }
    }


    private void validateDP(String vehicleCondition, long totalLoan, long dp) {
        double dpMin = vehicleCondition.equalsIgnoreCase("baru") ? 0.35 : 0.25;
        if (dp < dpMin * totalLoan) {
            throw new IllegalArgumentException("DP minimal " + (int)(dpMin * 100) + "% dari pinjaman");
        }
    }

    private String askLoop(String prompt, java.util.function.Predicate<String> validator, String errorMsg) {
        while (true) {
            String val = view.ask(prompt).trim();
            try {
                if (validator.test(val)) {
                    return val;
                } else {
                    view.showError(errorMsg);
                }
            } catch (Exception e) {
                view.showError(errorMsg);
            }
        }
    }
}