package projecttwo;

/**
 * FitnessClass is the class that defines a fitness class the member can check in. In this project, we just assume there
 * are only.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class FitnessClass {
    private final int NOTFOUND = -1;
    private String instructorName;
    private String fitnessClassName;
    private Time classTime;
    private final MemberDatabase studentsList = new MemberDatabase();
    private Location location;

    /**
     * Construct a FitnessClass object. Default constructor
     */
    public FitnessClass() {
    }

    public FitnessClass(String fitnessClassName, String instructorName, Time classTime, Location location) {
        this.fitnessClassName = fitnessClassName;
        this.instructorName = instructorName;
        this.classTime = classTime;
        this.location = location;
    }

    /**
     * Get the student database for this fitness class.
     *
     * @return The student database of this fitness class.
     */
    public MemberDatabase getStudentsList() {
        return studentsList;
    }

    /**
     * Get the name of this fitness class instructor.
     *
     * @return The name of this fitness class instructor.
     */
    public String getInstructor() {
        return instructorName;
    }

    /**
     * Get the name of this fitness class.
     *
     * @return The name of this fitness class.
     */
    public String getFitnessClassName() {
        return fitnessClassName;
    }

    /**
     * Get the time when this fitness class begins.
     *
     * @return The time when this fitness class begins.
     */
    public Time getTime() {
        return classTime;
    }

    /**
     * Set the time when this fitness class begins.
     *
     * @param classTime The time when this fitness class begins.
     */
    public void setTime(Time classTime) {
        this.classTime = classTime;
    }

    /**
     * Print the detail of the fitness class.
     * Notice that it would print the fitness class name, instructor name, time that class begins and all the members in
     * this fitness class(if exists).
     */
    public void printSchedule() {
        System.out.println(this.toString());
        if (studentsList.getSize() != 0) {
            System.out.println("- participants -");
        }
        studentsList.printSchedule();
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Check whether a member is checked in this fitness class or not.
     *
     * @param member A specific Member object that are going to be checked whether has checked in this course or not.
     * @return True if it is checked in, otherwise false.
     */
    public boolean isRegistered(Member member) {
        return studentsList.contains(member) != NOTFOUND;
    }

    /**
     * Check whether a member's membership has expired or not.
     *
     * @param member A specific Member object that going to be check if his membership has expired.
     * @return True if it is expired, false otherwise.
     */
    public boolean isExpired(Member member) {
        return member.getExpire().compareTo(new Date()) < 0;
    }

    public void addMember(Member member) {
        studentsList.add(member);
    }

    /**
     * Make a member drop from this fitness class.
     *
     * @param member   A specific Member object that need to drop from this class.
     * @param memberDb The main member database that stores all the members in all fitness classes.
     */
    public void drop(Member member, MemberDatabase memberDb) {
        if (member.getDob().isValidDob()) {
            if (studentsList.contains(member) != NOTFOUND) {
                studentsList.remove(member);
                System.out.println(member.getFname() + " " + member.getLname() + " dropped " + fitnessClassName + ".");
            } else {
                System.out.println(member.getFname() + " " + member.getLname() + " is not a participant in " + fitnessClassName + ".");
            }
        }
    }

    /**
     * Make the first character of a string be Capitalized
     *
     * @param className Input String that need to be changed
     */
    private String capitalizeString(String className) {
        char[] stringToChar = className.toCharArray();
        if (stringToChar[0] >= 'A' && stringToChar[0] <= 'Z') {
            return className;
        }
        stringToChar[0] -= 32;
        return String.valueOf(stringToChar);
    }

    @Override
    public String toString() {
        return fitnessClassName.toUpperCase() + " - " + instructorName.toUpperCase() + ", " + classTime.getDateTime() +
                ", " + location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FitnessClass) {
            FitnessClass fitnessClass = (FitnessClass) obj;
            if (fitnessClass.getLocation().compareLocation(location) == 0 && fitnessClass.getInstructor().
                    equalsIgnoreCase(instructorName) && fitnessClass.getFitnessClassName().
                    equalsIgnoreCase(fitnessClassName)) {
                return true;
            }
        }
        return false;
    }
}