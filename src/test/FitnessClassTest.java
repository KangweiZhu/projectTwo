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
    private final FitnessClass sampleFitnessClass = new FitnessClass("cs213","Chang",
            Time.AFTERNOON,Location.PISCATAWAY);

    /**
     * Test case 1: Check if a Member type member could be checked in
     */
    @Test
    public void test_checkin_a_memberType() {
        assertTrue(sampleFitnessClass.addMember(Kangwei));
    }

    /**
     * Test case 2: Check if a Family type member could be checked in
     */
    @Test
    public void test_checkin_a_FamilyType() {
        assertTrue(sampleFitnessClass.addMember(Michael));
    }

    /**
     * Test case 3: Check if a Premium type member could be checked in
     */
    @Test
    public void test_checkin_a_PremiumType() {
        assertTrue(sampleFitnessClass.addMember(Chang));
    }

    /**
     * Test case 4: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_member_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass.addMember(Kangwei));
    }

    /**
     * Test case 5: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_Family_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass.addMember(Michael));
    }


    /**
     * Test case 6: if one member has already checked in, then he could not check in to the same class
     */
    @Test
    public void test_add_if_Premium_already_checkedIn() {
        for (int i = 0; i < sampleMlist.length; i++) {
            sampleFitnessClass.addMember(sampleMlist[i]);
        }
        assertFalse(sampleFitnessClass.addMember(Chang));
    }

    /**
     * Test case 7: if one Premium type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_Premium_Type() {
        sampleFitnessClass.addMember(Chang);
        assertTrue(sampleFitnessClass.drop(Chang));
    }

    /**
     * Test case 8: if one Family type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_Family_Type() {
        sampleFitnessClass.addMember(Michael);
        assertTrue(sampleFitnessClass.drop(Michael));
    }
    /**
     * Test case 9: if one Member type member has been checked in, he should be able to remove from this class.
     */
    @Test
    public void test_drop_member_Type() {
        sampleFitnessClass.addMember(Kangwei);
        assertTrue(sampleFitnessClass.drop(Kangwei));
    }

    /**
     * Test case 10: if one Member type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_member_not_checkin(){
        assertFalse(sampleFitnessClass.drop(Kangwei));
    }

    /**
     * Test case 11: if one Family type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_Family_not_checkin(){
        assertFalse(sampleFitnessClass.drop(Michael));
    }

    /**
     * Test case 12: if one Premium type member hasn't been checked in yet, he could not be remove from this fitness class.
     */
    @Test
    public void test_drop_Premium_not_checkin(){
        assertFalse(sampleFitnessClass.drop(Chang));
    }


    /**
     *
     */
    @Test
    public void dropGuest() {
    }
}