package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReproductionSystemTest {

    @Test
    public void NewChildTest() {
        MutationSystem mutationSystem=new StandardMutation(0,4);
        ReproductionSystem reproductionSystem= new ReproductionSystem(0.5 , mutationSystem);
        Animal firstParent= new Animal(new Vector2d(2,2),"00000",150);
        Animal secondParent= new Animal(new Vector2d(2,2),"12345",50);
        Animal child= reproductionSystem.reproduce(firstParent,secondParent);
        assertEquals(new Vector2d(2,2),child.getPosition());
//        Niestety ze względu na elementy losowe mutacji i doboru stron sprawdzanie czy kod genetyczny mógł powstać
//        z części kodu rodziców był sprawdzany ręcznie poprzez sprawdzanie wartości wypisywanych,
//        a test sprawdza tylko, czy długość nowo powstałego kodu jest odpowednia
//        System.out.println(child.getGeneticCode());
        assertEquals(5,child.getGeneticLength());
        assertEquals(100,child.getEnergy());
        assertEquals(75,firstParent.getEnergy());
        assertEquals(25,secondParent.getEnergy());

//        Sprawdzam czy suma energii w wyniku rozmnażania nie zmienia się przy niecałkowitych wartościach podziału
        Animal tParent= new Animal(new Vector2d(2,2),"00000",155);
        Animal fParent= new Animal(new Vector2d(2,2),"12345",50);
        Animal secondChild = reproductionSystem.reproduce(fParent,tParent);
        assertEquals(5,secondChild.getGeneticLength());
        assertEquals(205,secondChild.getEnergy()+fParent.getEnergy()+tParent.getEnergy());

//        Test, czy przy generowaniu mutacji ograniczonych występują jakieś problemy
        MutationSystem secondSystem= new SlightMutation(4,5);
        ReproductionSystem reproductionSystem1 = new ReproductionSystem(0.5, secondSystem);
        Animal tsParent= new Animal(new Vector2d(2,2),"0000000",155);
        Animal fsParent= new Animal(new Vector2d(2,2),"0000000",50);
        Animal thirdChild = reproductionSystem1.reproduce(tsParent,fsParent);
        System.out.println(thirdChild.getGeneticCode());
        assertEquals(7,thirdChild.getGeneticLength());
        assertEquals(205,thirdChild.getEnergy()+fsParent.getEnergy()+tsParent.getEnergy());
    }


}
