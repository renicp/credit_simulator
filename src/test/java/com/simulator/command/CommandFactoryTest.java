package com.simulator.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simulator.command.impl.CalculationCommand;
import com.simulator.command.impl.ShowMenuCommand;
import com.simulator.controller.Controller;
import com.simulator.view.View;

class CommandFactoryTest {
    @Mock
    private Controller mockController;
    @Mock
    private View mockView;
    
    private CommandFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new CommandFactory(mockController, mockView);
    }

    @Test
    void testCreateNewCommand() {
        Command command = factory.create("new");
        
        assertNotNull(command);
        assertTrue(command instanceof CalculationCommand);
        assertEquals("new", command.getName());
    }

    @Test
    void testCreateShowCommand() {
        Command command = factory.create("show");
        
        assertNotNull(command);
        assertTrue(command instanceof ShowMenuCommand);
        assertEquals("show", command.getName());
    }

    @Test
    void testCreateInvalidCommand() {
        Command command = factory.create("invalid");
        
        assertNull(command);
    }
}