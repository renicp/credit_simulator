package com.simulator.view;

import java.util.List;

public interface View {
    String ask(String prompt);
    void showResults(List<String> results);
    void showError(String message);
}