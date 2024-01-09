package example.model;

import java.util.Random;

public class StandardMutation extends  GenericMutation{
    public StandardMutation(int minimal, int maksimal) {
        super(minimal, maksimal);
    }

    @Override
    public String singleMutation(String geneticCode) {
        int length = geneticCode.length();
        Random random=new Random();
        int idx=random.nextInt(length);
        String modifiedCode=geneticCode.substring(0,idx)+random.nextInt(8)+geneticCode.substring(idx+1,length);
        return modifiedCode;
    }
}
