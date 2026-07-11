import deterministic.GreedyScheduler;
import deterministic.Job;
import ecs100.UI;
import ecs100.UIButtonListener;
import probabilistic.ProbabilisticScheduler;

import java.util.Arrays;

public class Main implements UIButtonListener  {
    private UI ui;

    public Main() {
        UI.addButton("Probabilistic Scheduler", this);
        UI.addButton("Greedy Scheduler", this);
        UI.addButton("Quit", this);

        UI.repaintGraphics();
        this.ui = getUIfromECS();
    }

    private static UI getUIfromECS() {
        // Access UI.theUI via reflection since it may not be publicly accessible
        try {
            java.lang.reflect.Field theUIField = UI.class.getDeclaredField("theUI");
            theUIField.setAccessible(true);
            return  (UI) theUIField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void buttonPerformed(String name) {
        UI.clearText();

        switch (name) {
            case "Probabilistic Scheduler":
                try {
                    //fill array with 30 jobs, 50% simple and 50% complex
                    probabilistic.Job.Type[] jobs = new probabilistic.Job.Type[30];
                    for (int i = 0; i < jobs.length; i++) {
                        if (i % 2 == 1)
                            jobs[i] = probabilistic.Job.Type.SIMPLE;
                        else
                            jobs[i] = probabilistic.Job.Type.COMPLEX;
                    }
                    //assign to 5 machines
                    ProbabilisticScheduler tester = new ProbabilisticScheduler(ui, 5, jobs);
                    //must call solve and output
                    tester.solve();
                    tester.output();

                    //fill array with 30 jobs, 50% simple and 50% complex
                    probabilistic.Job.Type[] jobs2 = new probabilistic.Job.Type[30];
                    for (int i = 0; i < jobs2.length; i++) {
                        if (i % 2 == 1)
                            jobs2[i] = probabilistic.Job.Type.SIMPLE;
                        else
                            jobs2[i] = probabilistic.Job.Type.COMPLEX;
                    }
                    //sort first, see if it has impact
                    Arrays.sort(jobs2);
                    //assign to 5 machines
                    ProbabilisticScheduler tester2 = new ProbabilisticScheduler(ui, 5, jobs2);
                    //must call solve and output
                    tester2.solve();
                    tester2.output();
                } catch (Exception e) {
                    UI.println("Error: " + e.getMessage());
                }
                break;

            case "Greedy Scheduler":
                try {
                    //fill array with 30 jobs, 50% simple and 50% complex
                    Job.Type[] jobs = new Job.Type[30];
                    for (int i = 0; i < jobs.length; i++) {
                        if (i % 2 == 1) jobs[i] = Job.Type.SIMPLE;
                        else jobs[i] = Job.Type.COMPLEX;
                    }
                    //assign to 5 machines
                    GreedyScheduler tester = new GreedyScheduler(ui, 5, jobs);
                    //must call solve and output
                    tester.solve();
                    tester.output();

                    //fill array with 30 jobs, 50% simple and 50% complex
                    Job.Type[] jobs2 = new Job.Type[30];
                    for (int i = 0; i < jobs2.length; i++) {
                        if (i % 2 == 1) jobs2[i] = Job.Type.SIMPLE;
                        else jobs2[i] = Job.Type.COMPLEX;
                    }
                    //sort first, see if it has impact
                    Arrays.sort(jobs2);
                    //assign to 5 machines
                    GreedyScheduler tester2 = new GreedyScheduler(ui, 5, jobs2);
                    //must call solve and output
                    tester2.solve();
                    tester2.output();
                } catch (Exception e) {
                    UI.println("Error: " + e.getMessage());
                }
                break;

            case "Quit":
                UI.quit();
                break;

            default:
                UI.println("Unknown button: " + name);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
