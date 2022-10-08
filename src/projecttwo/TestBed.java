package projecttwo;

/**
 * The TestBed is for test uses. It implements the functionality of testing the Member class's compareTo() method and
 * testing the Date's class isValid() method.
 * The TestBed class and the TestBed object should only appear at the Date class's main method and Member class's main method.
 *
 * @author Kangwei Zhu Michael ISRAEL
 */
public class TestBed {
    protected static final int GREATER = 1;
    protected static final int EQUAL = 0;
    protected static final int SMALLER = -1;
    protected static final int INITIALIZECOUNT = 1;

    /**
     * Default constructor, used when creating a TestBed object
     */
    public TestBed() {
    }

    /**
     * This method is for testing the Member class's compareTo() method.
     * It will compare the expected output with the output that the current compareTo method will produce.
     *
     * @param testCandidateOne The first member object.
     * @param testCandidateTwo The second member object.
     * @param testCaseNum      The number of current test case.
     * @param expectedValue    The result what you expect firstMember.compareTo(secondMember) will return.
     * @return Return 1 if the expected output is the same as what compareTo() method returns. Return 0 otherwise.
     */
    public int testMemberCompareTo(Member testCandidateOne, Member testCandidateTwo, int testCaseNum, int expectedValue) {
        int outputValue = testCandidateOne.compareTo(testCandidateTwo);

        switch (testCaseNum) {
            case 1 -> System.out.println("**Test case #" + testCaseNum +
                    ": compareTo must return 0 if the argument Members stringed last + frist name is equal to .this " +
                    "Members stringed last and first name: " + testCandidateOne.getFname().toString()
                    + testCandidateOne.getLname().toString() + " " + testCandidateTwo.getFname().toString()
                    + testCandidateTwo.getLname().toString());
            case 2 -> System.out.println("**Test case #" + testCaseNum +
                    ": return 1 if argument Member last + first name is lexicographically greater than .this Members " +
                    "stringed last and first name" + testCandidateOne.getFname().toString()
                    + testCandidateOne.getLname().toString() + " " + testCandidateTwo.getFname().toString()
                    + testCandidateTwo.getLname().toString());
            case 3 -> System.out.println("**Test case #" + testCaseNum +
                    ": return -1 if argument Member last + first name is lexicographically less than .this Members " +
                    "stringed last and first name" + testCandidateOne.getFname().toString()
                    + testCandidateOne.getLname().toString() + " " + testCandidateTwo.getFname().toString()
                    + testCandidateTwo.getLname().toString());
            case 4 -> System.out.println("**Test case #" + testCaseNum +
                    ": return -1 if argument Members stringed last+first name is equal to .this Members stringed " +
                    "last+first name for the full length of .this Member but argument Member length is smaller than " +
                    ".this Member length " + testCandidateOne.getFname().toString()
                    + testCandidateOne.getLname().toString() + " " + testCandidateTwo.getFname().toString()
                    + testCandidateTwo.getLname().toString());
            case 6 -> System.out.println("**Test case #" + testCaseNum + "input string should not be case sensitive" +
                    testCandidateOne.getFname().toString() + testCandidateOne.getLname().toString() + " " +
                    testCandidateTwo.getFname().toString() + testCandidateTwo.getLname().toString());
            case 5 -> System.out.println("**Test case #" + testCaseNum +
                    ": return 1 if argument Members stringed last+first name is equal to .this Members stringed " +
                    "last+first name for the full length of .this Member but argument Member length is greater than " +
                    ".this Member length " + testCandidateOne.getFname().toString()
                    + testCandidateOne.getLname().toString() + " " + testCandidateTwo.getFname().toString()
                    + testCandidateTwo.getLname().toString());
        }
        System.out.println("compareTo() returns " + outputValue + (outputValue == expectedValue ? ", PASS." : ", FAIL.")
                + "\n");
        return outputValue == expectedValue ? 1 : 0;
    }

    /**
     * This method is for testing the Date class's isValid() method.
     * It will compare the expected value of isValid() that should return with the value isValid() is actually returning.
     *
     * @param testCandidate The Date object that are going to be tested
     * @param testCaseNum   The number of current test case
     * @param expectedValue The value what you expect Date.isValid() will produce.
     * @return if the expected output is the same as what isValid() method  returns, then return 1. Return 0 otherwise.
     */
    public int testDateIsValid(Date testCandidate, int testCaseNum, boolean expectedValue) {
        switch (testCaseNum) {
            case 1 -> System.out.println("**Test case #" + testCaseNum +
                    ": The method shall not accept any date with the year before 1900: " + testCandidate.toString());
            case 2 -> System.out.println("**Test case #" + testCaseNum +
                    ": Number of days in February for a nonleap year shall be 28: " + testCandidate.toString());
            case 3 -> System.out.println("**Test case #" + testCaseNum +
                    ": The range of month should be between 1-12: " + testCandidate.toString());
            case 4 -> System.out.println("**Test case #" + testCaseNum +
                    ": For Janurary, March, May, July, August, October, and December, there are 31 days: "
                    + testCandidate.toString());
            case 5 -> System.out.println("**Test case #" + testCaseNum +
                    ": For April, June, September, and November, there are 31 days: " + testCandidate.toString());
            case 6 -> System.out.println("**Test case #" + testCaseNum +
                    ": In a non-leap year, February should have 28 days: " + testCandidate.toString());
            case 7 -> System.out.println("**Test case #" + testCaseNum +
                    ": For every valid date, the day should be 0 : " + testCandidate.toString());
            case 8 -> System.out.println("**Test case #" + testCaseNum +
                    ": If the year is greater thean 1900, then we consider the year is ok: " + testCandidate.toString());
            case 9 -> System.out.println("**Test case #" + testCaseNum +
                    ": For a non-leap year, the number of days in February should be 28: " + testCandidate.toString());
            case 10 -> System.out.println("**Test case #" + testCaseNum +
                    ": The range of month should be between 1-12: " + testCandidate.toString());
            case 11 -> System.out.println("**Test case #" + testCaseNum +
                    ": For Janurary, March, May, July, August, October, and December, there are 31 days: "
                    + testCandidate.toString());
            case 12 -> System.out.println("**Test case #" + testCaseNum +
                    ": For April, June, September, and November, there are 31 days: " + testCandidate.toString());
            case 13 -> System.out.println("**Test case #" + testCaseNum +
                    ": In a leap year, February should have 29 days: " + testCandidate.toString());
            case 14 -> System.out.println("**Test case #" + testCaseNum +
                    ": Days must be greater than 0 " + testCandidate.toString());
        }
        boolean outputValue = testCandidate.isValid();
        System.out.println("isValid() returns " + outputValue + (outputValue == expectedValue ? ", PASS." : ", FAIL.") + "\n");
        return outputValue == expectedValue ? 1 : 0;
    }
}
