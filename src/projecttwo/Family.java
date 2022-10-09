package projecttwo;

public class Family extends Member {
    protected int numOfGuestPass;

    public Family() {
        numOfGuestPass = 1;
    }

    public Family(String firstName, String lastName, Date dob, Date expirationDate, Location location) {
        super(firstName, lastName, dob, expirationDate, location);
        this.numOfGuestPass = 1;
    }

    public int getNumOfGuestPass() {
        return numOfGuestPass;
    }

    public void setNumOfGuestPass() {
        this.numOfGuestPass--;
    }

    @Override
    public double membershipFee() {
        return 59.99;
    }

    //not sure if we are allowed to use getClass() method here. obviously getClass() is better than instanceof since it reduces the nums of duplicate codes.
    //if we are not allowed, then we have to use instanceof in memberDB print() method.
    /*@Override
    public String toString() {
        return super.toString() + ", (" + getClass().getSimpleName() + ") Guest-pass remaining: " + this.numOfGuestPass;
    }*/
}
