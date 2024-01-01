package org.example.model;

import java.util.Random;

public class ReproductionSystem {

    private final double usedEnergy;

    private final MutationSystem mutationSystem;

    public ReproductionSystem(double usedEnergy, MutationSystem mutationSystem){
        this.usedEnergy=usedEnergy;
        this.mutationSystem=mutationSystem;
    }

    private String genomPart(int side, String GeneticCode, int length){
        if(side==0) {
            return GeneticCode.substring(0,length);
        }
        else{
            int totalLength = GeneticCode.length();
            return GeneticCode.substring(totalLength-length,totalLength);
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
        double allEnergy = firstParent.getEnergy()+secondParent.getEnergy();
        if (firstParent.getEnergy()>secondParent.getEnergy()){
            topParent=firstParent;
            bottomParent=secondParent;
        }
        else{
            topParent=secondParent;
            bottomParent=firstParent;
        }

        if(side==0){
            int longerPart= (int)((topParent.getEnergy()/allEnergy)* topParent.getGeneticLength());
            newGeneticCode=genomPart(0, topParent.getGeneticCode(), longerPart);
            int shorterPart=topParent.getGeneticLength()-longerPart;
            newGeneticCode=newGeneticCode+genomPart(1,bottomParent.getGeneticCode(), shorterPart);
        }
        else{
            int longerPart= (int)((topParent.getEnergy()/allEnergy)* topParent.getGeneticLength());
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
