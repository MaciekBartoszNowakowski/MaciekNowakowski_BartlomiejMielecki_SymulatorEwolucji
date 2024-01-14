package org.example.model;

import java.util.Random;
import java.util.*;

public abstract class GenericMutation implements MutationSystem{

    private final int minimal;
    private final int maksimal;

    public GenericMutation(int minimal,int maksimal){
        this.minimal=minimal;
        this.maksimal=maksimal;
    }

    public abstract String singleMutation(String geneticCode, int index);

    @Override
    public String mutate(String geneticCode) {
        Random random =new Random();
        int mutationAmount = random.nextInt(maksimal-minimal+1)+minimal;

        int codeLength= geneticCode.length();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < codeLength; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i=0; i<mutationAmount;i++){

            geneticCode=singleMutation(geneticCode,indexes.get(i));
        }
        return geneticCode;
    }
}
