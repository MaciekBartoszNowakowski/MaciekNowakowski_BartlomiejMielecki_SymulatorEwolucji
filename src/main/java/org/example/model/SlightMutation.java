package org.example.model;

import java.util.Random;

public class SlightMutation extends GenericMutation {
    public SlightMutation(int minimal, int maksimal) {
        super(minimal, maksimal);
    }

    @Override
    public String singleMutation(String geneticCode) {
        int length = geneticCode.length();
        Random random = new Random();
        int idx = random.nextInt(length);
        int check= random.nextInt(2);
        int slightChange=0;
        if (check==0){
            slightChange=-1;
        }
        else {
            slightChange=1;
        }
        int changedGenom = geneticCode.charAt(idx) -'0';
        changedGenom = (changedGenom + slightChange) % 8;
        String modifiedCode = geneticCode.substring(0, idx) + changedGenom + geneticCode.substring(idx + 1, length);
        return modifiedCode;
    }
}
