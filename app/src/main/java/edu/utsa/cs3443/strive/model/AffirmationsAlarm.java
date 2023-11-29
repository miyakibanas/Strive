// java/edu/utsa/cs3443/strive/model/Affirmation.java
package edu.utsa.cs3443.strive.model;

public class AffirmationsAlarm {
    private String affirmationText;

    public AffirmationsAlarm(String text) {
        this.affirmationText = text;
    }

    public String getText() {
        return affirmationText;
    }
}
