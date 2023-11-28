// java/edu/utsa.cs3443/strive/controller/AffirmationController.java
package edu.utsa.cs3443.strive.controller;

import edu.utsa.cs3443.strive.model.AffirmationsAlarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AffirmationsController {
    private List<AffirmationsAlarm> affirmations;
    private Random random;

    public AffirmationsController() {
        random = new Random();
        affirmations = new ArrayList<>();
        loadAffirmations();
    }

    private void loadAffirmations() {
        affirmations.add(new AffirmationsAlarm("I am confident and capable."));
        // Add more hardcoded affirmations...
        affirmations.add(new AffirmationsAlarm("I am strong and resilient."));
    }

    public String getNextAffirmation() {
        if (!affirmations.isEmpty()) {
            int index = random.nextInt(affirmations.size());
            return affirmations.get(index).getText();
        }
        return null;
    }
}
