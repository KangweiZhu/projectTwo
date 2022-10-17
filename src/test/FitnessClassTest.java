package test;

import org.junit.Test;
import projecttwo.*;

import static org.junit.Assert.*;

public class FitnessClassTest {
    private final Member Kangwei = new Member("Kangwei", "Zhu", new Date("01/01/2002"),
            new Date("12/31/2022"), Location.EDISON);
    private final Family Michael = new Family("Michael", "Israel", new Date("01/01/2002"),
            new Date("12/31/2022"), Location.EDISON);
    private final Premium Chang = new Premium("Chang", "Lily", new Date("01/01/2002"),
            new Date("12/31/2022"), Location.EDISON);
    private Member[] sampleMlist = {Chang, Michael, Kangwei};
    private final FitnessClass sampleFitnessClass_cs213 = new FitnessClass("cs213", "Chang",
            Time.AFTERNOON, Location.PISCATAWAY);
    GymManager sampleGymManager = new GymManager();
    /**
     * Test case 1: Check if a Member type member could be checked in
     */
    @Test
    public void test_checkin_a_memberType() {
        assertTrue(sampleFitnessClass_cs213.addMember(Kangwei));
    }

    /**
     * Test case 2: Check if a Family type member could be checked in
     */
    @Test
    public void test_checkin_a_FamilyType() {
        assertTrue(sampleFitnessClass_cs213.addMember(Michael));
    }

    /**
     * Test case 3: Check if a Premium type member could be checked in
     */
    @Test
    public void test_checkin_a_PremiumType() {
        assertTrue(sampleFitnessClass_cs213.addMember(Chang));
    }

    /**
     * Test case 4: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_member_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass_cs213.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass_cs213.addMember(Kangwei));
    }

    /**
     * Test case 5: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_Family_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass_cs213.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass_cs213.addMember(Michael));
    }


    /**
     * Test case 6: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_Premium_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass_cs213.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass_cs213.addMember(Chang));
    }

    /**
     * Test case 7: if one Premium type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_Premium_Type() {
        sampleFitnessClass_cs213.addMember(Chang);
        assertTrue(sampleFitnessClass_cs213.drop(Chang));
    }

    /**
     * Test case 8: if one Family type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_Family_Type() {
        sampleFitnessClass_cs213.addMember(Michael);
        assertTrue(sampleFitnessClass_cs213.drop(Michael));
    }

    /**
     * Test case 9: if one Member type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_member_Type() {
        sampleFitnessClass_cs213.addMember(Kangwei);
        assertTrue(sampleFitnessClass_cs213.drop(Kangwei));
    }

    /**
     * Test case 10: if one Member type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_member_not_checkin() {
        assertFalse(sampleFitnessClass_cs213.drop(Kangwei));
    }

    /**
     * Test case 11: if one Family type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_Family_not_checkin() {
        assertFalse(sampleFitnessClass_cs213.drop(Michael));
    }

    /**
     * Test case 12: if one Premium type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_Premium_not_checkin() {
        assertFalse(sampleFitnessClass_cs213.drop(Chang));
    }

    /**
     * Test case 13: if one guest has been added to the guestList, then we can remove him
     */
    @Test
    public void test_drop_a_guest() {
        sampleFitnessClass_cs213.addGuest(Chang);
        assertTrue(sampleFitnessClass_cs213.dropGuest(Chang));
    }

    /**
     * Test case 14: We could add a guest into the guestList
     */
    @Test
    public void test_add_a_Guest() {
        assertTrue(sampleFitnessClass_cs213.addGuest(Chang));
    }

    /**
     * Test case 15: For a memberType, if he already registered in one class at for example 9:30, then he will not
     * be able to check in any other class at 9:30 since there is time conflict
     */
    @Test
    public void test_time_conflict(){
        FitnessClass nineThirtyClassOne = new FitnessClass("PILATES", "JENNIFER",
                Time.MORNING, Location.BRIDGEWATER);
        FitnessClass nineThirtyClassTwo = new FitnessClass("PILATES", "DAVIS",
                Time.MORNING, Location.PISCATAWAY);
        sampleGymManager.testCheckIn(nineThirtyClassOne,Chang);
        FitnessClass[] sampleSchedule = {nineThirtyClassOne, nineThirtyClassTwo};
        sampleGymManager.addSampleClassSchedule(sampleSchedule,2);
        assertFalse(sampleGymManager.testCheckIn(nineThirtyClassTwo,Chang));
    }

    /**
     * Test case 16: A member with invalid expirationDate will not be checkin into any class
     */
    @Test
    public void test_invalid_expireDate_checkin(){
        Member member = new Member("abc","cba",new Date("2/2/2000"),new Date("2/31/-11"),Location.EDISON);
        assertFalse(sampleGymManager.testCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 17: A member with invalid expirationDate will not be checkin into any class
     */
    @Test
    public void test_invalid_DOB_checkin(){
        Member member = new Member("abc","cba",new Date("2/2/0"),new Date("2/31/2022"),Location.EDISON);
        assertFalse(sampleGymManager.testCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 18: A member cannot have guest for checking in.
     */
    @Test
    public void test_invalid_memberShip_checkin(){
        Member member = new Member("abc","cba",new Date("2/2/0"),new Date("2/31/2022"),Location.EDISON);
        assertFalse(sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 19: A Family membership can only checkin the fitnessClass at the same location as the membership's
     * location.
     */
    @Test
    public void test_invalid_Location_checkin(){
        Member member = new Family("abc","cba",new Date("2/2/2001"),new Date("2/31/2999"),Location.EDISON);
        assertFalse(sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 20: A Family membership could only have one guest for checkin
     */
    @Test
    public void test_more_than_one_guest_Family_checkin(){
        Member member = new Family("abc","cba",new Date("2/2/2001"),new Date("2/31/2999"),Location.PISCATAWAY);
        sampleGymManager.testCheckIn(sampleFitnessClass_cs213,member);
        sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member);
        assertFalse(sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 20: A Premium membership could only have Three guest for checkin
     */
    @Test
    public void test_more_than_one_guest_Premium_checkin(){
        Member member = new Premium("abc","cba",new Date("2/2/2001"),new Date("2/31/2999"),Location.PISCATAWAY);
        sampleGymManager.testCheckIn(sampleFitnessClass_cs213,member);
        sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member);
        sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member);
        sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member);
        assertFalse(sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member));
    }

    /**
     * Test case 21: A premium membership's guest could only check in at membership location
     */
    @Test
    public void test_Premium_Location_checkin(){
        Premium member = new Premium("abc","cba",new Date("2/2/2001"),new Date("2/31/2999"),Location.EDISON);
        assertFalse(sampleGymManager.testGuestCheckIn(sampleFitnessClass_cs213,member));
    }
}