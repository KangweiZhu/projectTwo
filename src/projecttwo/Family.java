package projecttwo;

public class Family extends Member {
    protected int numOfGuestPass;

    public Family() {
        numOfGuestPass = 1;
    }

    public Family(String firstName, String lastName, Date dob, Date expirationDate,Location location) {
        super(firstName,lastName,dob,expirationDate,location);
        memberShipFee = 59.99;
    }

    public int getNumOfGuestPass() {
        return numOfGuestPass;
    }


    @Override
    public double membershipFee() {
        return this.memberShipFee;
    }
}
