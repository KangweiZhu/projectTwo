package projecttwo;

import java.io.File;

public class Family extends Member {
    private int numOfGuestPass;

    public Family() {
        this.numOfGuestPass = 1;
    }

    public Family(int numOfGuestPass){
        this.numOfGuestPass = numOfGuestPass;
    }

    public int getNumOfGuestPass() {
        return numOfGuestPass;
    }

    @Override
    public double membershipFee() {
        return 59.99;
    }
}
