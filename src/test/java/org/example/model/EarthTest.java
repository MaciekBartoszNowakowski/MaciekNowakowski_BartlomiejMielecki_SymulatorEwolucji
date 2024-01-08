package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EarthTest {
    @Test
    public void test(){
        MutationSystem mutationSystem = new SlightMutation(2, 5);
        Earth earth = new Earth(14, 2, 10, 10, 10, 100, 2, 2, 0.3, mutationSystem);
        assertEquals(10, earth.getAnimals().size());
//        assertEquals(4, earth.getGrasses().size());
        earth.dayCycle();
    }
}
