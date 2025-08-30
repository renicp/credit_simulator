package com.simulator.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleFactoryTest {

    @Test
    void testCreateCar() {
        Vehicle vehicle = VehicleFactory.create("mobil", "baru");
        
        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Car);
        assertEquals("Mobil", vehicle.getType());
        assertEquals("baru", vehicle.getCondition());
        assertEquals(8.0, vehicle.getBaseInterest());
    }

    @Test
    void testCreateMotorcycle() {
        Vehicle vehicle = VehicleFactory.create("motor", "bekas");
        
        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Motorcycle);
        assertEquals("Motor", vehicle.getType());
        assertEquals("bekas", vehicle.getCondition());
        assertEquals(9.0, vehicle.getBaseInterest());
    }

    @Test
    void testCreateInvalidVehicleType() {
        assertThrows(IllegalArgumentException.class, () -> 
            VehicleFactory.create("truck", "baru"));
    }
}