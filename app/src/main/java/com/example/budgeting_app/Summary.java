package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();

        int income = intent.getIntExtra("income", -1);
        int spent = intent.getIntExtra("spent", -1);
        int doneGoals = intent.getIntExtra("doneGoals", -1);
        int totalGoals = intent.getIntExtra("totalGoals", -1);

        TextView incomeSummary = findViewById(R.id.incomeSummary);
        incomeSummary.setText("$"+Integer.toString(income));

        TextView spentSummary = findViewById(R.id.expenseSummary);
        spentSummary.setText("$"+Integer.toString(spent));

        TextView savedSummary = findViewById(R.id.savedSummary);
        savedSummary.setText("$"+Integer.toString(income - spent));

        TextView goalsSummary = findViewById(R.id.goalsSummary);
        goalsSummary.setText(doneGoals + "/" + totalGoals);

        ProgressBar piechart = findViewById(R.id.stats_progressbar);
        piechart.setMax(income);
        piechart.setProgress(spent);

        TextView money_left = findViewById(R.id.Money_Left);
        money_left.setText("$"+spent+"/"+income);

    }

    public void Homepage(View view) {
//        Intent intent= new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
