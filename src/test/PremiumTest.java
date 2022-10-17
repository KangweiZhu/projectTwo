package test;

import org.junit.Test;
import projecttwo.Premium;

import static org.junit.Assert.*;

/**
 * This class is for Junit test : membershipFee() method in Premium class
 */
public class PremiumTest {
    /**
     * Default constructor
     */
    public PremiumTest(){

    }
    /**
     * Test case1 : The annual Membership Fee should be $659.89
     */
    @Test
    public void test_expected_annualFee() {
        double annualFee = 659.89;
        Premium testPremium = new Premium();
        boolean expectedRes = annualFee == testPremium.membershipFee();
        assertTrue(expectedRes);
    }

    /**
     * Test case2 : The annual Membership Fee should be $659.89
     */
    @Test
    public void test_expected_annualFee_again() {
        double annualFee = -1;
        Premium testPremium = new Premium();
        boolean expectedRes = annualFee == testPremium.membershipFee();
        assertFalse(expectedRes);
    }
}