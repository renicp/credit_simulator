package com.simulator.controller;

public interface Controller {
    void runInteractive();
    void runWithFile(String filename);
    void loadFromExternalApi();
}