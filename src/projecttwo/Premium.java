package projecttwo;

public class Premium extends Family{
    public Premium(){
        memberShipFee = 59.99;
        numOfGuestPass = 3;
    }
    public Premium(String firstName, String lastName, Date dob, Date expireDate, Location location){
        super(firstName,lastName,dob,expireDate,location);
        numOfGuestPass = 3;
        memberShipFee = 59.99;
    }

    @Override
    public double membershipFee() {
        return this.memberShipFee;
    }
}
