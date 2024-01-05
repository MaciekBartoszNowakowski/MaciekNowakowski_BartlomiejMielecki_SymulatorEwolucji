package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void DeliverNextDirectionTest() {
        MapDirection currDirrection = MapDirection.NORTH;
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.NORTHEAST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.EAST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.SOUTHEAST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.SOUTH,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.SOUTHWEST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.WEST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.NORTHWEST,currDirrection);
        currDirrection = currDirrection.next();
        assertEquals(MapDirection.NORTH,currDirrection);
        currDirrection = currDirrection.next();
    }



}
