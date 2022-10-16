package test;

import org.junit.Test;
import projecttwo.Date;
import static org.junit.Assert.*;

public class DateTest {

    /**
     * Test case 1: The Date class will not accept any date with the year before 1900
     */
    @Test
    public void test_Year_less_than_1900() {
        Date testDate = new Date("11/21/800");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 2: The Date class will accept any date with the year after 1900
     */
    @Test
    public void test_year_more_than_1900() {
        Date testDate = new Date("11/21/1905");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 3: There should be 29 days in February for a non leap year
     */
    @Test
    public void test_num_of_days_nonLeapFeb(){
        Date testDate = new Date("2/29/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 4: The valid range for the month should be between 1(Jan) and 12(Dec)
     */
    @Test
    public void test_range_of_month(){
        Date testDate = new Date("13/29/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 5: January, March, May, July, Auguest, October, December should have 31 days
     */
    @Test
    public void test_num_of_days_March(){
        Date testDate = new Date("3/32/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 6: April June September and November should have 30 days
     */
    @Test
    public void test_num_of_days_April(){
        Date testDate = new Date("4/31/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 7: Feburary should have 28 days in non-leap year
     */
    @Test
    public void test_non_leap_year_again(){
        Date testDate = new Date("2/31/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 8: Test if Date object are greater than 0
     */
    @Test
    public void test_day_greater_than_zero(){
        Date testDate = new Date("3/-27/2018");
        assertFalse(testDate.isValid());
    }

    /**
     * Test case 9: The number of days in a non-leap year Feburary is less than or equal to 28
     */
    @Test
    public void test_non_leap_Feb_again(){
        Date testDate = new Date("2/27/2018");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 10: The range of month in one year should be between 1 and 12
     */

    @Test
    public void test_range_of_month_again(){
        Date testDate = new Date("1/29/2018");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 11: January, March, May, July, Augest, October, December should have 31 days
     */
    @Test
    public void test_num_of_days_March_again(){
        Date testDate = new Date("3/31/2018");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 12: April, June, September, and November should have 30 days
     */
    @Test
    public void test_num_of_days_April_again(){
        Date testDate = new Date("4/30/2018");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 13: In a leap year, Feburary should have 29 days
     */
    @Test
    public void test_num_of_days_Feb_Leap_Year(){
        Date testDate = new Date("2/29/2020");
        assertTrue(testDate.isValid());
    }

    /**
     * Test case 14: Days in each month should be greater than 0
     */
    @Test
    public void test_num_of_days_again(){
        Date testDate = new Date("3/27/2018");
        assertTrue(testDate.isValid());
    }
}