package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void moveTest() {
        Animal testedAnimal=new Animal(new Vector2d(2,2),"0172",10);
        testedAnimal.move(testedAnimal.nextPosition());
        assertEquals(new Vector2d(2,3),testedAnimal.getPosition());
        testedAnimal.move(testedAnimal.nextPosition());
        assertEquals(new Vector2d(3,4),testedAnimal.getPosition());
        testedAnimal.move(testedAnimal.nextPosition());
        assertEquals(new Vector2d(3,5),testedAnimal.getPosition());
        testedAnimal.move(testedAnimal.nextPosition());
        assertEquals(new Vector2d(4,5),testedAnimal.getPosition());
    }

    @Test
    public void getChildrenAmountTest(){
        MutationSystem mutationSystem=new StandardMutation(0,4);
        ReproductionSystem reproductionSystem= new ReproductionSystem(0.5 , mutationSystem);
        Animal firstParent= new Animal(new Vector2d(2,2),"00000",150);
        Animal secondParent= new Animal(new Vector2d(2,2),"12345",50);
        Animal firstChild= reproductionSystem.reproduce(firstParent,secondParent);
        Animal secondChild=reproductionSystem.reproduce(firstParent,firstChild);
        assertEquals(2,firstParent.getChildrenAmount());
        assertEquals(1,secondParent.getChildrenAmount());
        assertEquals(1,firstChild.getChildrenAmount());
        assertEquals(0,secondChild.getChildrenAmount());
    }

    @Test
    public void getOffspringsAmountTest(){
        MutationSystem mutationSystem=new StandardMutation(0,4);
        ReproductionSystem reproductionSystem= new ReproductionSystem(0.5 , mutationSystem);
        Animal firstParent= new Animal(new Vector2d(2,2),"00000",150);
        Animal secondParent= new Animal(new Vector2d(2,2),"12345",50);
        Animal firstChild= reproductionSystem.reproduce(firstParent,secondParent);
        Animal secondChild=reproductionSystem.reproduce(firstParent,firstChild);
        assertEquals(2,firstParent.getOffspringsAmount());
        assertEquals(2,secondParent.getOffspringsAmount());
        assertEquals(1,firstChild.getOffspringsAmount());
        assertEquals(0,secondChild.getOffspringsAmount());
    }





}
