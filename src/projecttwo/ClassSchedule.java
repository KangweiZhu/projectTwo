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
}
