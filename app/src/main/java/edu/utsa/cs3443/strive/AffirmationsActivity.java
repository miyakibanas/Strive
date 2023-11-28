// java/edu/utsa/cs3443/strive/activity/AffirmationsActivity.java
package edu.utsa.cs3443.strive;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.utsa.cs3443.strive.R;
import edu.utsa.cs3443.strive.controller.AffirmationsController;

public class AffirmationsActivity extends AppCompatActivity {
    private TextView affirmationTextView;
    private AffirmationsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations);

        affirmationTextView = findViewById(R.id.affirmationTextView);
        controller = new AffirmationsController();
        showNextAffirmation();

        findViewById(R.id.nextAffirmationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextAffirmation();
            }
        });
    }

    private void showNextAffirmation() {
        String affirmation = controller.getNextAffirmation();
        if (affirmation != null) {
            affirmationTextView.setText(affirmation);
        } else {
            // Handle case where no more affirmations are available
        }
    }
}
