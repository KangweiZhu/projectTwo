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
    private final int INDEX_OF_CLASS_NAME = 0;
    private final int MODE_C = 0;
    private final int MODE_D = 1;
    private final int MODE_CG = 2;
    private final int MODE_DG = 3;
    private final int INDEX_OF_INSTRUCTOR = 1;
    private final int INDEX_OF_FIRSTNAME = 1;
    private final int INDEX_OF_LASTNAME = 2;
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
     * It will handle all the command listed in the instruction.
     */
    public void run() {
        System.out.println("Gym Manager running..." + "\n");
        Scanner sc = new Scanner(System.in);
        String command;
        String[] cmdLine;
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
                case "C" -> doCAndD(cmdLine, MODE_C);
                case "D" -> doCAndD(cmdLine, MODE_D);
                case "LS" -> LS();
                case "LM" -> LM();
                case "AF" -> A(cmdLine, 1);
                case "AP" -> A(cmdLine, -1);
                case "PF" -> PF();
                case "CG" -> doCAndD(cmdLine, MODE_CG);
                case "DG" -> doCAndD(cmdLine, MODE_DG);
                case "Q" -> flag = false;
                default -> System.out.println(cmdLine[0] + " is an invalid command!");
            }
        }
        System.out.println("Gym manager terminated.");
        sc.close();
    }

    /**
     * This method will add a Member Object into the member Database.
     *
     * @param cmdLine The information(attributes) of a Member object.
     * @param addType 0 : Member Object   1: Family Object which extends Member Object  -1: Premium Object with extends
     *                Family Object
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
                        (curDate.getYear() + 1));
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
            expireDate = new Date(curDate.getMonth() + "/" + curDate.getDay() + "/" + (curDate.getYear() + 1));
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
            System.out.println("\n-Fitness Classes-");
            for (int i = 0; i < numOfClasses; i++) {
                classSchedule.getFitnessClasses()[i].printInfo();
            }
            System.out.println("-end of class list.\n");
        }
    }

    /**
     * This method handle the command "LS", which is the command that load the fitness class schedule from a text file
     * to the class schedule in the software system.
     */
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
                FitnessClass fitnessClass = new FitnessClass(infos[INDEX_OF_CLASS_NAME], infos[INDEX_OF_INSTRUCTOR],
                        classTime, classLocation);
                fitnessClasses[index++] = fitnessClass;
            }
        }
        classSchedule = new ClassSchedule(fitnessClasses, fitnessClasses.length);
        classSchedule.printClassSchedule();
    }

    /**
     * This method handles "LM" command, which loads the historical member information from a text file to the member
     * database.
     */
    private void LM() {
        String fileName = "memberList.txt";
        String[] lines = readFiles(fileName);
        System.out.println("\n-list of members loaded-");
        for (int i = 1; i < lines.length; i++) {
            String cmdLine = lines[i];
            String[] infos = cmdLine.split("\\s+");
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
        System.out.println("-end of list-\n");
    }

    /**
     * This method handles the command "PF", which is print the list of members with the next billing statement
     * membership fees.
     */
    private void PF() {
        memberDB.printByMembershipFees();
    }

    /**
     * This method is used when dropping a member's guest to a specific class
     *
     * @param fitnessClass The specific fitness class that the guest is going to be dropped.
     * @param guest        The guest
     * @return True if successfully dropped, false otherwise.
     */
    private boolean doDG(FitnessClass fitnessClass, Member guest) {
        return fitnessClass.dropGuest(guest);
    }

    /**
     * This method is used when checking in a member's guest to a specific fitness class.
     *
     * @param fitnessClass The specific fitness class that the guest is going to be checked into.
     * @param member       The member which bring his guest.
     * @return True if successfully added, otherwise false.
     */
    private boolean doCG(FitnessClass fitnessClass, Member member) {
        int numOfPass = 0;
        boolean flag = false;
        if (member instanceof Family) {
            numOfPass = ((Family) member).getNumOfGuestPass();
            if (numOfPass == 0) {
                System.out.println(member.getFname() + " " + member.getLname() + " ran out of guest pass.");
            } else {
                if (member.getLocation().compareLocation(fitnessClass.getLocation()) == 0) {
                    ((Family) member).setNumOfGuestPass(-1);
                    flag = fitnessClass.addGuest(member);
                    System.out.println(member.getFname() + " " + member.getLname() + " (guest) checked in " +
                            fitnessClass.toString());
                    fitnessClass.printSchedule();
                    System.out.println();
                } else {
                    Location location = fitnessClass.getLocation();
                    String zipCode = location.getZipCode();
                    String county = location.getCounty().toUpperCase();
                    System.out.println(member.getFname() + " " + member.getLname() + " Guest checking in " +
                            location + ", " + zipCode + ", " + county + " - guest location restriction.");
                }
            }
        } else {
            System.out.println("Standard membership - guest check-in is not allowed.");
        }
        return flag;
    }

    /**
     * This method is used for sperating the string typed array command lines. Do the checks before checkin a member/guest
     * / drop a member/guest and then dispatch it to the methods that do the check in/drop member/guest.
     *
     * @param cmdLine The cmdLine array which has the attribute of the member.
     * @param mode    0 : check in a member into a fitness Class.
     *                1 : drop a member from a fitness Class.
     *                2 : check in a guest into a fitness Class.
     *                3 : drop a guest from fitness Class.
     */
    private void doCAndD(String[] cmdLine, int mode) {
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
            newMember = memberDB.getMember(newMember);
        } else {
            System.out.println(fName + " " + lName + " " + dob + " is not in the database.");
            return;
        }
        if (isValidLocation(newlocation)) {
            location = Location.valueOf(newlocation.toUpperCase());
        } else {
            return;
        }
        FitnessClass fitnessClass = new FitnessClass(className, instructorName, null, location);
        if (classSchedule.isFitnessClassExist(fitnessClass)) {
            fitnessClass = classSchedule.getFitnessClass(fitnessClass);
            if (fitnessClass == null) {
                return;
            }
            if (mode == MODE_C) {
                doCheckIn(fitnessClass, newMember);
            } else if (mode == MODE_D) {
                fitnessClass.drop(newMember);
            } else if (mode == MODE_DG) {
                doDG(fitnessClass, newMember);
            } else if (mode == MODE_CG) {
                doCG(fitnessClass, newMember);
            }
        }
    }

    /**
     * check whether a member is already in database or not
     *
     * @param member The member that are going to be checked.
     * @return True if not exist, otherwise false.
     */
    private boolean checkDB(Member member) {
        if (memberDB.contains(member) < 0) {
            if (member.getDob().isValidDob() && member.getExpire().isValidExpiration()) {
                return true;
            }
        } else {
            System.out.println(member.getFname() + " " + member.getLname() + " is already in the database.");
        }
        return false;
    }

    /**
     * Check if there is time conflict when a member want to check in a fitnessClass.
     * That is, if the member has already registered a fitness class that the time of the fitnessClass he wants to check
     * in. That is considered as time conflict.
     *
     * @param fitnessClass The fitness class that the mmember wants to check in.
     * @param member       The member that are going to check in a class
     * @return True if there is time conflict, false otherwise.
     */
    private boolean isTimeConflict(FitnessClass fitnessClass, Member member) {
        String classTime = fitnessClass.getTime().getDateTime();
        for (int i = 0; i < classSchedule.getNumClasses(); i++) {
            if (classSchedule.getFitnessClasses()[i].isRegistered(member)) {
                if (classTime.equals(classSchedule.getFitnessClasses()[i].getTime().getDateTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is used when check in a member into a fitnessClass. It also does the checking before add the member into
     * the fitnessClass's student list.
     *
     * @param fitnessClass The fitness class this member wants to check in
     * @param member       The member that are going to be checked in
     * @return True if successfully checked in, false otherwise.
     */
    private boolean doCheckIn(FitnessClass fitnessClass, Member member) {
        String fName = member.getFname();
        String lName = member.getLname();
        Location location = fitnessClass.getLocation();
        String zipCode = location.getZipCode();
        String county = location.getCounty().toUpperCase();
        boolean flag = false;
        if (!fitnessClass.isRegistered(member)) {
            if (!fitnessClass.isExpired(member)) {
                if (!isTimeConflict(fitnessClass, member)) {
                    if (member instanceof Family) {
                        fitnessClass.addMember(member);
                        System.out.println(fName + " " + lName + " checked in " + fitnessClass.toString());
                        fitnessClass.printSchedule();
                        System.out.println();
                    } else {
                        if (fitnessClass.getLocation().compareLocation(member.getLocation()) == 0) {
                            flag = fitnessClass.addMember(member);
                            System.out.println(fName + " " + lName + " checked in " + fitnessClass.toString());
                            fitnessClass.printSchedule();
                            System.out.println();
                        } else {
                            System.out.println(fName + " " + lName + " checking in " + location + ", " + zipCode + ", "
                                    + county + " - standard membership location restriction.");
                        }
                    }
                } else {
                    System.out.println("Time conflict - " + fitnessClass.toString() + ", " + zipCode + ", "
                            + county);
                }
            } else {
                System.out.println(fName + " " + lName + " " + member.getDob() + " membership expired.");
            }
        } else {
            System.out.println(fName + " " + lName + " already checked in.");
        }
        return flag;
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

    /**
     * This method is used only for the junit test: check in before add member.
     *
     * @param fitnessClass The fitness class that the member is gonna checked in
     * @param member       The member object that will check into one fitness class
     * @return True if successfully checked in, otherwise false.
     */
    public boolean testCheckIn(FitnessClass fitnessClass, Member member) {
        return doCheckIn(fitnessClass, member);
    }

    /**
     * This method is used only for the junit test: check in before add guest.
     *
     * @param fitnessClass The fitness class that the member's guest is gonna checked in
     * @param member       The member's guest that will check into one fitness class
     * @return True if successfully checked in, otherwise false.
     */
    public boolean testGuestCheckIn(FitnessClass fitnessClass, Member member) {
        return doCG(fitnessClass, member);
    }

    /**
     * This method is used only for the junit test: add member.
     *
     * @param fitnessClasses The sample class schedule. Which is the array that holds all the fitness classes in this
     *                       fitness chain.
     * @param numOfClass     The number of fitness Class this fitness chain has.
     */
    public void addSampleClassSchedule(FitnessClass[] fitnessClasses, int numOfClass) {
        this.classSchedule = new ClassSchedule(fitnessClasses, fitnessClasses.length);
    }

    /**
     * This method is used when reading lines from text file.
     *
     * @param fileName The name of file
     * @return The String array that contains all the lines. Each element in this array is the one line in the text file.
     */
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