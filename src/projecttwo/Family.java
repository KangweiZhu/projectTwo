package projecttwo;

public class Family extends Member {
    protected int numOfGuestPass;
    private final double ONE_TIME_FEE = 29.99;
    private final double MONTHLY_FEE = 59.99;
    private final int MONTH_NUM = 3;
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

    public void setNumOfGuestPass(int num) {
        this.numOfGuestPass += num;
    }

    @Override
    public double membershipFee() {
        return ONE_TIME_FEE + MONTHLY_FEE * MONTH_NUM;
    }

    //not sure if we are allowed to use getClass() method here. obviously getClass() is better than instanceof since it reduces the nums of duplicate codes.
    //if we are not allowed, then we have to use instanceof in memberDB print() method.
    /*@Override
    public String toString() {
        return super.toString() + ", (" + getClass().getSimpleName() + ") Guest-pass remaining: " + this.numOfGuestPass;
    }*/
}
