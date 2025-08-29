package com.simulator.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.simulator.model.Loan;
import com.simulator.model.Vehicle;
import com.simulator.model.VehicleFactory;
import com.simulator.model.ApiResponse;
import com.simulator.service.LoanCalculatorService;
import com.simulator.service.LoanCalculator;
import com.simulator.view.View;
import com.simulator.api.HttpClient;
import com.google.gson.Gson;

public class LoanController implements Controller {
    private View view;
    private HttpClient httpMethod; 

    public LoanController(View view, HttpClient httpClient) {
        this.view = view;
        this.httpMethod = httpClient;
    }

    public void runInteractive() {
        try {
            String vehicleType = askWithValidation("Input Jenis Kendaraan (Mobil/Motor): ", this::validateVehicleType);
            String vehicleCondition = askWithValidation("Input Kondisi (Baru/Bekas): ", this::validateVehicleCondition);
            int year = Integer.parseInt(askWithValidation("Input Tahun Kendaraan (yyyy): ", 
                val -> validateYear(Integer.parseInt(val), vehicleCondition)));
            long totalLoan = Long.parseLong(askWithValidation("Input Jumlah Pinjaman (<= 1 M): ", 
                val -> validateTotalLoan(Long.parseLong(val))));
            int tenor = Integer.parseInt(askWithValidation("Input Tenor Pinjaman (1-6): ", 
                val -> validateTenor(Integer.parseInt(val))));
            long downPayment = Long.parseLong(askWithValidation("Input DP: ", 
                val -> validateDP(vehicleCondition, totalLoan, Long.parseLong(val))));

            Vehicle vehicle = VehicleFactory.create(vehicleType, vehicleCondition);
            Loan loan = new Loan(vehicle, year, totalLoan, tenor, downPayment);

            LoanCalculatorService calc = new LoanCalculator();
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

            validateVehicleType(vehicleType);
            validateVehicleCondition(vehicleCondition);
            validateYear(year, vehicleCondition);
            validateTotalLoan(totalLoan);
            validateTenor(tenor);
            validateDP(vehicleCondition, totalLoan, dp);

            Vehicle vehicle = VehicleFactory.create(vehicleType, vehicleCondition);
            Loan loan = new Loan(vehicle, year, totalLoan, tenor, dp);

            LoanCalculatorService calc = new LoanCalculator();
            List<String> results = calc.calculateInstallments(loan);
            view.showResults(results);
        } catch (Exception e) {
            view.showError("File error: " + e.getMessage());
        }
    }

    public void loadFromExternalApi() {
        try {
            System.out.println("Connecting to server...");
            String response = this.httpMethod.get();
            System.out.println("Response received: " + (response.length() > 100 ? response.substring(0, 100) + "..." : response));
            
            if (!response.equalsIgnoreCase("ERROR")) {
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

                validateVehicleType(apiResponse.getVehicleType());
                validateVehicleCondition(apiResponse.getVehicleCondition());
                validateYear(apiResponse.getVehicleYear(), apiResponse.getVehicleCondition());
                validateTotalLoan(apiResponse.getTotalLoanAmount());
                validateTenor(apiResponse.getLoanTenure());
                validateDP(apiResponse.getVehicleCondition(),
                        apiResponse.getTotalLoanAmount(),
                        apiResponse.getDownPayment());

                Vehicle vehicle = VehicleFactory.create(
                        apiResponse.getVehicleType(),
                        apiResponse.getVehicleCondition()
                );

                Loan loan = new Loan(
                        vehicle,
                        apiResponse.getVehicleYear(),
                        apiResponse.getTotalLoanAmount(),
                        apiResponse.getLoanTenure(),
                        apiResponse.getDownPayment()
                );

                LoanCalculatorService calc = new LoanCalculator();
                List<String> results = calc.calculateInstallments(loan);
                view.showResults(results);
            } else {
                this.view.showError("Load Data From Server ERROR!!!");
            }
        } catch (Exception e) {
            this.view.showError("Load Data From Server Crash! " + e.getMessage());
        }
    }


    private void validateVehicleType(String vehicleType) {
        if (!vehicleType.equalsIgnoreCase("mobil") && !vehicleType.equalsIgnoreCase("motor")) {
            throw new IllegalArgumentException("Jenis kendaraan harus 'Mobil' atau 'Motor'");
        }
    }

    private void validateVehicleCondition(String vehicleCondition) {
        if (!vehicleCondition.equalsIgnoreCase("baru") && !vehicleCondition.equalsIgnoreCase("bekas")) {
            throw new IllegalArgumentException("Kondisi harus 'Baru' atau 'Bekas'");
        }
    }

    private void validateYear(int year, String vehicleCondition) {
        int currentYear = java.time.Year.now().getValue();
        if (vehicleCondition.equalsIgnoreCase("baru") && year < currentYear - 1) {
            throw new IllegalArgumentException("Kendaraan BARU tidak boleh lebih lama dari " + (currentYear - 1));
        }
        if (vehicleCondition.equalsIgnoreCase("bekas") && year > currentYear) {
            throw new IllegalArgumentException("Kendaraan BEKAS tidak boleh di masa depan (maksimal " + currentYear + ")");
        }
    }

    private void validateTotalLoan(long totalLoan) {
        if (totalLoan <= 0 || totalLoan > 1_000_000_000L) {
            throw new IllegalArgumentException("Jumlah pinjaman harus angka <= 1 milyar");
        }
    }

    private void validateTenor(int tenor) {
        if (tenor < 1 || tenor > 6) {
            throw new IllegalArgumentException("Tenor harus 1-6 tahun");
        }
    }

    private void validateDP(String vehicleCondition, long totalLoan, long dp) {
        double dpMin = vehicleCondition.equalsIgnoreCase("baru") ? 0.35 : 0.25;
        if (dp < dpMin * totalLoan) {
            throw new IllegalArgumentException("DP minimal " + (int)(dpMin * 100) + "% dari pinjaman");
        }
    }

    private String askWithValidation(String prompt, java.util.function.Consumer<String> validator) {
        while (true) {
            String val = view.ask(prompt).trim();
            try {
                validator.accept(val);
                return val;
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }
}