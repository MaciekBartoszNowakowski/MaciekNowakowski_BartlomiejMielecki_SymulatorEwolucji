package org.example.model;

import org.junit.jupiter.api.Test;

public class HellGateTest {
    @Test
    public void test(){
        MutationSystem mutationSystem = new SlightMutation(2, 5);
        HellGate hellGate = new HellGate(2, 2, 100, 100, 10, 100, 2, 2, 0.3, mutationSystem);
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
        hellGate.dayCycle();
    }
}
