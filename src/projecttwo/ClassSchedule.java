package projecttwo;

public class ClassSchedule {
    private FitnessClass[] fitnessClasses;
    private int numClasses;

    public ClassSchedule(){
        numClasses = 0;
        fitnessClasses = null;
    }

    public ClassSchedule(FitnessClass[] fitnessClasses, int numClasses){
        this.fitnessClasses = fitnessClasses;
        this.numClasses = numClasses;
    }

    public int getNumClasses(){
        return this.numClasses;
    }

    public FitnessClass[] getFitnessClasses(){
        return this.fitnessClasses;
    }

    public void printClassSchedule(){
        System.out.println("-Fitness classes loaded-");
        for (int i = 0; i < numClasses; i++) {
            System.out.println(fitnessClasses[i].toString());
        }
        System.out.println("-end of class list.");
    }

    public boolean isFitnessClassExist(FitnessClass checkClass){
        String className = checkClass.getFitnessClassName();
        String instructor = checkClass.getInstructor();
        Location location = checkClass.getLocation();
        boolean nameFlag = false;
        boolean instructorFlag = false;
        boolean locationFlag = false;
        for (int i = 0; i < numClasses; i++) {
            if (fitnessClasses[i].getFitnessClassName().equalsIgnoreCase(className)){
                nameFlag = true;
            }
            if (fitnessClasses[i].getInstructor().equalsIgnoreCase(instructor)){
                instructorFlag = true;
            }
            if (fitnessClasses[i].getLocation().compareLocation(location) == 0){
                locationFlag = true;
            }
        }
        if (!nameFlag){
            System.out.println(className + " - class does not exist.");
            return false;
        }else if (!instructorFlag){
            System.out.println(instructor + " - instructor does not exist.");
            return false;
        }else if (!locationFlag){
            System.out.println(className + " by " + instructor + " does not exist at " + location);
            return false;
        }else{
            return true;
        }
    }

    public FitnessClass getFitnessClass(FitnessClass fitnessClass){
        for (int i = 0; i < numClasses; i++) {
            if (fitnessClass.equals(fitnessClasses[i])){
                return fitnessClasses[i];
            }
        }
        return null;
    }
}
