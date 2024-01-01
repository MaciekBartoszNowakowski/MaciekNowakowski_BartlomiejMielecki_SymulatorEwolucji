package org.example.model;

import java.util.Random;

public abstract class GenericMutation implements MutationSystem{

    private final int minimal;
    private final int maksimal;

    public GenericMutation(int minimal,int maksimal){
        this.minimal=minimal;
        this.maksimal=maksimal;
    }

    public abstract String singleMutation(String geneticCode);

    @Override
    public String mutate(String geneticCode) {
        Random random =new Random();
        int mutationAmount = random.nextInt(maksimal-minimal+1)+minimal;

        for (int i=0; i<mutationAmount;i++){;
            geneticCode=singleMutation(geneticCode);
        }
        return geneticCode;
    }
}
