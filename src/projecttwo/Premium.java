package projecttwo;

public class Premium extends Family{
    public Premium(){
        this.numOfGuestPass = 3;
    }
    public Premium(String firstName, String lastName, Date dob, Date expireDate, Location location){
        super(firstName,lastName,dob,expireDate,location);
        this.numOfGuestPass = 3;
    }

    @Override
    public double membershipFee() {
        return 59.99;
    }
}
