package abst;

import ecs100.UI;

public abstract class Scheduler {
    private UI ui;

    public Scheduler(UI ui) {
        this.ui = ui;
    }

    protected void println(String s) {
        if (ui == null) {
            System.out.println(s);
        } else {
            UI.println("");
            ui.println(s);
        }
    }
}
