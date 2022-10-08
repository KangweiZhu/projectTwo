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
    private final int INDEX_OF_EXPIRATION_DATE = 4;
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
                case "A" -> A(cmdLine,0);
                case "R" -> R(cmdLine);
                case "P" -> P();
                case "PC" -> PC();
                case "PN" -> PN();
                case "PD" -> PD();
                case "S" -> S();
                /*case "C" -> C(cmdLine);
                case "D" -> D(cmdLine);*/
                case "LS" -> LS();
                case "LM" -> LM();
                case "AF" -> AF();
                case "AP" -> AP();
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
    private void A(String[] cmdLine, int memberType) {
        String memberLocation = cmdLine[INDEX_OF_LOCATION].toUpperCase();
        Date dob = new Date(cmdLine[INDEX_OF_DOB]);
        Date expireDate = new Date(cmdLine[INDEX_OF_EXPIRATION_DATE]);
        if (dob.isValidDob() && expireDate.isValidExpiration() && isValidLocation(memberLocation)) {
            Member member = new Member(cmdLine[INDEX_OF_FIRSTNAME], cmdLine[INDEX_OF_LASTNAME], dob, expireDate, Location.valueOf(memberLocation));
            if (memberDB.add(member)) {
                System.out.println(member.getFname() + " " + member.getLname() + " added.");
            } else {
                System.out.println(member.getFname() + " " + member.getLname() + " already in the database.");
            }
        }
        if (memberType == 0){

        }else{

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
            System.out.println("Fitness class schedule is empty.");
        } else {
            System.out.println("-Fitness Classes-");
            for (int i = 0; i < numOfClasses; i++) {
                classSchedule.getFitnessClasses()[i].printSchedule();
            }
            System.out.println();
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
        for (int i = 1; i < lines.length; i++) {
            String cmdLine = lines[i];
            String[] infos = cmdLine.split("\\s");
            A(infos,1);
        }
    }

    private void AF() {

    }

    private void AP() {

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

    private void C(String[] cmdLine) {
    String fName = cmdLine[INDEX_OF_FIRSTNAME + 1];
    String lName = cmdLine[INDEX_OF_LASTNAME + 1];
    String className = cmdLine[INDEX_OF_CLASS_NAME];
    Date dob = new Date(cmdLine[INDEX_OF_DOB + 1]);
    Member findMember = new Member(fName, lName, dob);
    if (!findMember.getDob().isValid()){
    System.out.println("DOB "+ findMember.getDob() + ": invalid calendar date!");
    return;
    }
    if (memberDB.contains(findMember) != ISNOTFOUND) {
    findMember = memberDB.returnMember(memberDB.contains(findMember));
    for (int i = 0; i < fitnessClasses.length; i++) {
    if ((fitnessClasses[i].getFitnessClassName()).equalsIgnoreCase(className)) {
    fitnessClasses[i].checkIn(findMember, className, fitnessClasses, memberDB);
    return;
    }
    }
    System.out.println(className + " class does not exist.");
    } else {
    System.out.println(fName + " " + lName + " " + dob.toString() + " is not in the database.");
    }
    }
     */


    /**
     * The method is used to drop the fitness classes after the member checked in to a class.
     * Will not allow the member to drop the class if the member is not checked in, the date of birth is invalid,
     * or the fitness class does not exist.

     private void D(String[] cmdLine) {
     String className = cmdLine[INDEX_OF_CLASS_NAME];
     String fName = cmdLine[INDEX_OF_FIRSTNAME + 1];
     String lName = cmdLine[INDEX_OF_LASTNAME + 1];
     Date dob = new Date(cmdLine[INDEX_OF_DOB + 1]);
     Member member = new Member(fName, lName, dob);
     for (int i = 0; i < fitnessClasses.length; i++) {
     if (fitnessClasses[i].getFitnessClassName().equalsIgnoreCase(className)) {
     fitnessClasses[i].drop(new Member(fName, lName, dob), memberDB);
     return;
     }
     }
     System.out.println(className + " class does not exist.");
     }
     */

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