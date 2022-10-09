package projecttwo;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/**
 * This class is for the running of a Gym.
 * Given commands can either add, delete, or print a list of members
 * Given commands can also add, delete, or print a list of classes for members
 *
 * @author Kangwei Zhu, Michael Israel
 */
public class GymManager {
    private final int ISNOTFOUND = -1;
    private final int INDEX_OF_COMMAND = 0;
    private final int INDEX_OF_FIRST_CLASS = 0;
    private final int INDEX_OF_CLASS_NAME = 0;
    private final int INDEX_OF_SECOND_CLASS = 1;
    private final int INDEX_OF_INSTRUCTOR = 1;
    private final int INDEX_OF_FIRSTNAME = 1;
    private final int INDEX_OF_LASTNAME = 2;
    private final int INDEX_OF_THIRD_CLASS = 2;
    private final int INDEX_OF_DAYTIME = 2;
    private final int INDEX_OF_DOB = 3;
    private final int INDEX_OF_LOCATION = 3;
    private final int MEMBER_AND_FAMILY_EXPIRE = 3;
    private final int INDEX_OF_EXPIRATION_DATE = 4;
    private final int INDEX_OF_CHECKIN_FNAME = 4;
    private final int INDEX_OF_CHECKIN_LNAME = 5;
    private final int INDEX_OF_CHECKIN_DOB = 6;

    MemberDatabase memberDB = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    /**
     * Default constructor.
     */
    public GymManager() {
    }

    /**
     * The method is used when the gym starts for the day.
     * Given commands can either add, delete, or print a list of members
     * Given commands can also add, delete, or print a list of classes for members
     */
    public void run() {
        System.out.println("Gym Manager running..." + "\n");
        Scanner sc = new Scanner(System.in);
        String command;
        String[] cmdLine;
        /*addClasses();*/
        boolean flag = true;
        int countEmptyLine = 1;
        while (flag) {
            command = sc.nextLine();
            cmdLine = command.split("\\s");
            if (command.length() == 0) {
                if (countEmptyLine > 0) {
                    System.out.println();
                    countEmptyLine--;
                    continue;
                } else {
                    continue;
                }
            }
            switch (cmdLine[0]) {
                case "A" -> A(cmdLine, 0);
                case "R" -> R(cmdLine);
                case "P" -> P();
                case "PC" -> PC();
                case "PN" -> PN();
                case "PD" -> PD();
                case "S" -> S();
                case "C" -> CAndD(cmdLine,0);
                case "D" -> CAndD(cmdLine,1);
                case "LS" -> LS();
                case "LM" -> LM();
                case "AF" -> A(cmdLine, 1);
                case "AP" -> A(cmdLine, -1);
                case "PF" -> PF();
                case "CG" -> CG();
                case "DG" -> DG();
                case "Q" -> flag = false;
                default -> System.out.println(cmdLine[0] + " is an invalid command!");
            }
        }
        System.out.println("Gym manager terminated.");
        sc.close();
    }

    /**
     * The method is used when adding a member to the database.
     *
     * @param cmdLine Takes in location, dob, full name, and expire date of an individual
     */
    private void A(String[] cmdLine, int addType) {
        String firstName = cmdLine[INDEX_OF_FIRSTNAME];
        String lastName = cmdLine[INDEX_OF_LASTNAME];
        Date dob = new Date(cmdLine[INDEX_OF_DOB]);
        String newLocation = cmdLine[INDEX_OF_LOCATION + 1];
        Location location = null;
        if (isValidLocation(newLocation)) {
            location = Location.valueOf(newLocation.toUpperCase());
        } else {
            return;
        }
        Date curDate = new Date();
        Date expireDate;
        Member newMember;
        if (addType == 0 || addType == 1) {
            if (curDate.checkNextYear(MEMBER_AND_FAMILY_EXPIRE) >= 0) {
                expireDate = new Date(curDate.checkNextYear(MEMBER_AND_FAMILY_EXPIRE) + "/" + curDate.getDay() + "/" +
                        curDate.getYear() + 1);
            } else {
                expireDate = new Date(curDate.getMonth() + MEMBER_AND_FAMILY_EXPIRE + "/" + curDate.getDay() + "/" +
                        curDate.getYear());
            }
            if (addType == 0) {
                newMember = new Member(firstName, lastName, dob, expireDate, location);
            } else {
                newMember = new Family(firstName, lastName, dob, expireDate, location);
            }
        } else {
            expireDate = new Date(curDate.getMonth() + "/" + curDate.getDay() + "/" + curDate.getYear() + 1);
            newMember = new Premium(firstName, lastName, dob, expireDate, location);
        }
        if (checkDB(newMember)) {
            memberDB.add(newMember);
            System.out.println(newMember.getFname() + " " + newMember.getLname() + " added.");
        }
    }

    /**
     * The method is used when removing a member to the database.
     *
     * @param cmdLine Takes in dob and full name of a member
     */
    private void R(String[] cmdLine) {
        Date date = new Date(cmdLine[INDEX_OF_DOB]);
        Member member = new Member(cmdLine[INDEX_OF_FIRSTNAME], cmdLine[INDEX_OF_LASTNAME], date);
        if (memberDB.remove(member)) {
            System.out.println(member.getFname() + " " + member.getLname() + " removed.");
        } else {
            System.out.println(member.getFname() + " " + member.getLname() + " is not in the database.");
        }
    }

    /**
     * The method is used when printing/displaying a list of members in database.
     */
    private void P() {
        if (memberDB.getSize() == 0) {
            memberDB.print();
        } else {
            System.out.println();
            System.out.println("-list of members-");
            memberDB.print();
        }
    }

    /**
     * The method is used to display the list of members in the database.
     * Ordered by the county names and then the zip codes;
     * that is, if the locations are in the same county, ordered by the zip codes.
     */
    private void PC() {
        memberDB.printByCounty();
    }

    /**
     * The method is used to display the list of members in the database.
     * Ordered by the members’ last names and then first names;
     * that is, if two members have the same last name, ordered by the first name.
     */
    private void PN() {
        memberDB.printByName();
    }

    /**
     * The method is used to display the list of members in the database ordered by the expiration dates.
     * If two expiration dates are the same, their order doesn’t matter.
     */
    private void PD() {
        memberDB.printByExpirationDate();
    }

    /**
     * The method is used to display the fitness class schedule.
     * A fitness class shall include the fitness class name, instructor’s name, the time of the class,
     * and the list of members who have already checked in today
     */
    private void S() {
        int numOfClasses = classSchedule.getNumClasses();
        if (numOfClasses == 0) {
            System.out.println("Fitness class schedule is empty.\n");
        } else {
            System.out.println("-Fitness Classes-");
            for (int i = 0; i < numOfClasses; i++) {
                classSchedule.getFitnessClasses()[i].printSchedule();
            }
            System.out.println("-end of class list.\n");
        }
    }

    private void LS() {
        String fileName = "classSchedule.txt";
        String[] lines = readFiles(fileName);
        FitnessClass[] fitnessClasses = new FitnessClass[Integer.parseInt(lines[0])];
        int index = 0;
        for (int i = 1; i < lines.length; i++) {
            String cmdLine = lines[i];
            String[] infos = cmdLine.split("\\s");
            String location = infos[INDEX_OF_LOCATION].toUpperCase();
            Time classTime = Time.valueOf(infos[INDEX_OF_DAYTIME].toUpperCase());
            Location classLocation = Location.valueOf(infos[INDEX_OF_LOCATION].toUpperCase());
            if (isValidLocation(location)) {
                FitnessClass fitnessClass = new FitnessClass(infos[INDEX_OF_CLASS_NAME], infos[INDEX_OF_INSTRUCTOR], classTime, classLocation);
                fitnessClasses[index++] = fitnessClass;
            }
        }
        classSchedule = new ClassSchedule(fitnessClasses, fitnessClasses.length);
        classSchedule.printClassSchedule();
    }

    private void LM() {
        String fileName = "memberList.txt";
        String[] lines = readFiles(fileName);
        System.out.println("\n-list of members loaded-");
        for (int i = 1; i < lines.length; i++) {
            String cmdLine = lines[i];
            String[] infos = cmdLine.split("\\s");
            String firstName = infos[INDEX_OF_FIRSTNAME - 1];
            String lastName = infos[INDEX_OF_LASTNAME - 1];
            Date dob = new Date(infos[INDEX_OF_DOB - 1]);
            Date expireDate = new Date(infos[INDEX_OF_EXPIRATION_DATE - 1]);
            String location = infos[INDEX_OF_LOCATION + 1];
            Location newLocation = null;
            if (isValidLocation(location)) {
                newLocation = Location.valueOf(location.toUpperCase());
                Member pastMember = new Member(firstName, lastName, dob, expireDate, newLocation);
                memberDB.add(pastMember);
                System.out.println(pastMember.toString());
            }
        }
        System.out.println("-end of list-");
    }

    private void PF() {

    }

    private void CG() {

    }

    private void DG() {

    }

    /**
     * The method is used for members to check in to a fitness class.
     * Not allowed to be added if -->  the membership has expired, the member does not exist,
     * the date of birth is invalid, the fitness class does not exist,
     * there is a time conflict with other fitness classes, or the member has already checked in
     *
     * @param cmdLine Takes in class name, dob, and full name of a member
     */
    private void CAndD(String[] cmdLine, int mode) {
        String fName = cmdLine[INDEX_OF_CHECKIN_FNAME];
        String lName = cmdLine[INDEX_OF_CHECKIN_LNAME];
        String className = cmdLine[INDEX_OF_CLASS_NAME + 1];
        String newlocation = cmdLine[INDEX_OF_LOCATION];
        Location location;
        Date dob = new Date(cmdLine[INDEX_OF_CHECKIN_DOB]);
        if (!dob.isValidDob()) {
            return;
        }
        String instructorName = cmdLine[INDEX_OF_INSTRUCTOR + 1];
        Member newMember = new Member(fName, lName, dob);
        if (memberDB.contains(newMember) >= 0) {
            newMember = memberDB.getMember(newMember); // if the database contain this member, then retrieve this member from database. this member could be any type so we need check type
        } else {
            System.out.println(fName + " " + lName + " " + dob + " is not in the database.");
            return;
        }
        if (isValidLocation(newlocation)) {
            location = Location.valueOf(newlocation.toUpperCase());
        } else {
            return;
        }
        //check if the fitness class is in the array of fitness class
        FitnessClass fitnessClass = new FitnessClass(className, instructorName, null, location);
        if (classSchedule.isFitnessClassExist(fitnessClass)) {
            fitnessClass = classSchedule.getFitnessClass(fitnessClass);
            if (fitnessClass == null) {
                return;
            }
            if(mode == 0) {
                doCheckIn(fitnessClass, newMember);
            }else {
                doDrop(fitnessClass, newMember);
            }
        }
    }

    public boolean checkDB(Member member) {
        if (memberDB.contains(member) < 0) {
            if (member.getDob().isValidDob() && member.getExpire().isValidExpiration()) {
                return true;
            }
        } else {
            System.out.println(member.getFname() + " " + member.getLname() + " is already in the database.");
        }
        return false;
    }

    private boolean isTimeConflict(FitnessClass fitnessClass, Member member) {
        String className = fitnessClass.getFitnessClassName();
        int index = 0;
        String[] times = new String[classSchedule.getNumClasses()];
        String time = " ";
        FitnessClass[] fitnessClasses = classSchedule.getFitnessClasses();
        for (int i = 0; i < times.length; i++) {
            if ((fitnessClasses[i].getFitnessClassName()).equalsIgnoreCase(className)) {
                time = fitnessClasses[i].getTime().getDateTime();
            }
        }
        for (int i = 0; i < fitnessClasses.length; i++) {
            if ((fitnessClasses[i].getFitnessClassName()).equalsIgnoreCase(className)) {
                continue;
            }
            if (fitnessClasses[i].getStudentsList().contains(member) != ISNOTFOUND) {
                if (time.equalsIgnoreCase(fitnessClasses[i].getTime().getDateTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void doCheckIn(FitnessClass fitnessClass, Member member) {
        String fName = member.getFname();
        String lName = member.getLname();
        if (!fitnessClass.isRegistered(member)) {
            if (!fitnessClass.isExpired(member)) {
                if (!isTimeConflict(fitnessClass, member)) {
                    if (member instanceof Family) {
                        fitnessClass.addMember(member);
                        System.out.println(fName + " " + lName + " checked in " + fitnessClass.toString());
                        fitnessClass.printSchedule();
                    } else if (member instanceof Member) {
                        if (fitnessClass.getLocation().compareLocation(member.getLocation()) == 0) {
                            fitnessClass.addMember(member);
                            System.out.println(fName + " " + lName + " checked in " + fitnessClass.toString());
                            fitnessClass.printSchedule();
                        } else {
                            System.out.println(fName + " " + lName + " checking in " +
                                    fitnessClass.getLocation().toString() + " - standard membership location restriction.");
                        }
                    }
                } else {
                    System.out.println("Time conflict - " + fitnessClass.toString());
                }
            } else {
                System.out.println(fName + " " + lName + " " + member.getDob() + " membership expired.");
            }
        } else {
            System.out.println(fName + " " + lName + " already checked in.");
        }
    }

    /**
     * The method is used to drop the fitness classes after the member checked in to a class.
     * Will not allow the member to drop the class if the member is not checked in, the date of birth is invalid,
     * or the fitness class does not exist.
     */
    private void doDrop(FitnessClass fitnessClass, Member member) {
        String fName = member.getFname();
        String lName = member.getLname();
        Date dob = member.getDob();
        String className = fitnessClass.getFitnessClassName();
        fitnessClass.drop(member);
    }

    /**
     * The method is used to see if a fitness class located at a location.
     *
     * @param loc The location of a fitness class
     * @return true if there is a fitness class located at the location. false otherwise
     */
    private boolean isValidLocation(String loc) {
        for (Location location : Location.values()) {
            if (location.toString().equalsIgnoreCase(loc)) {
                return true;
            }
        }
        System.out.println(loc + ": invalid location!");
        return false;
    }

    private String[] readFiles(String fileName) {
        File inputFile = new File(fileName);
        try {
            Scanner sc = new Scanner(inputFile);
            String line;
            int countNumOfLine = 0;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                countNumOfLine++;
            }
            String[] lines = new String[countNumOfLine + 1];
            int index = 1;
            lines[0] = Integer.toString(countNumOfLine);
            sc = new Scanner(inputFile);
            for (int i = 1; i < lines.length; i++) {
                lines[i] = sc.nextLine();
            }
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}