package com.simulator.command;

public interface Command {
    void execute();
    String getName(); 
    String getDescription();
}