package org.example.model;

import java.util.Random;

public class ReproductionSystem {

    private final float usedEnergy;

    private final MutationSystem mutationSystem;

    public ReproductionSystem(float usedEnergy, MutationSystem mutationSystem){
        this.usedEnergy=usedEnergy;
        this.mutationSystem=mutationSystem;
    }

    private String genomPart(int side, String GeneticCode, int length){
        if(side==0) {
            String newGenom= GeneticCode.substring(0,length+1);
            return newGenom;
        }
        else{
            int totalLength = GeneticCode.length();
            String newGenom= GeneticCode.substring(totalLength-length,totalLength);
            return newGenom;
        }

    }

    private int giveEnergy(Animal firstAnimal,Animal secondAnimal){
        int childEnergy=0;
        int givenEnergy = (int)(firstAnimal.getEnergy()*usedEnergy);
        firstAnimal.subtractEnergy(givenEnergy);
        childEnergy+=givenEnergy;
        givenEnergy = (int)(secondAnimal.getEnergy()*usedEnergy);
        secondAnimal.subtractEnergy(givenEnergy);
        childEnergy+=givenEnergy;
        return childEnergy;

    }



    public Animal reproduce(Animal firstParent, Animal secondParent){
        Random random = new Random();
        String newGeneticCode;
        int side= random.nextInt(2);
        Animal topParent;
        Animal bottomParent;
        int allEnergy = firstParent.getEnergy()+secondParent.getEnergy();
        if (firstParent.getEnergy()>secondParent.getEnergy()){
            topParent=firstParent;
            bottomParent=secondParent;
        }
        else{
            topParent=secondParent;
            bottomParent=firstParent;
        }

        if(side==0){
            int longerPart= (topParent.getEnergy()/allEnergy)* topParent.getGeneticLength();
            newGeneticCode=genomPart(0, topParent.getGeneticCode(), longerPart);
            int shorterPart=topParent.getGeneticLength()-longerPart;
            newGeneticCode=newGeneticCode+genomPart(1,bottomParent.getGeneticCode(), shorterPart);
        }
        else{
            int longerPart= (topParent.getEnergy()/allEnergy)* topParent.getGeneticLength();
            newGeneticCode=genomPart(1, topParent.getGeneticCode(), longerPart);
            int shorterPart=topParent.getGeneticLength()-longerPart;
            newGeneticCode=genomPart(0,bottomParent.getGeneticCode(), shorterPart)+newGeneticCode;
        }

        newGeneticCode=mutationSystem.mutate(newGeneticCode);


        int givenEnergy=giveEnergy(topParent,bottomParent);
        Animal child= new Animal(topParent.getPosition(),newGeneticCode,givenEnergy);

        child.parents.add(topParent);
        child.parents.add(bottomParent);
        topParent.children.add(child);
        bottomParent.children.add(child);

        child.setCurrentGenome(random.nextInt(topParent.getGeneticLength()));

        return child;
    }




}
