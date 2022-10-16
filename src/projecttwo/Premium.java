package projecttwo;

public class Premium extends Family{
    private final double MONTHLY_FEE = 59.99;
    private final double MONTH_NUM = 11;
    public Premium(){
        this.numOfGuestPass = 3;
    }
    public Premium(String firstName, String lastName, Date dob, Date expireDate, Location location){
        super(firstName,lastName,dob,expireDate,location);
        this.numOfGuestPass = 3;
    }

    @Override
    public double membershipFee() {
        return MONTHLY_FEE * MONTH_NUM;
    }
}
