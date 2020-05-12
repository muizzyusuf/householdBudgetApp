package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Goals extends AppCompatActivity {

     ArrayList<String> goal_list;
     ArrayList<String> done_goals;

    public ArrayList<String> getGoal_list() {
        return goal_list;
    }

    public void setGoal_list(ArrayList<String> goal_list) {
        this.goal_list = goal_list;
    }

    public ArrayList<String> getDone_goals() {
        return done_goals;
    }

    public void setDone_goals(ArrayList<String> done_goals) {
        this.done_goals = done_goals;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        Bundle extras = getIntent().getExtras();
        setDone_goals(extras.getStringArrayList("done_goals"));
        setGoal_list(extras.getStringArrayList("goal_list"));

        for (String x : getGoal_list()) {
                if (!(getDone_goals().contains(x))) {
                    final LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayoutScroll);
                    String goal = x;
                    final TextView tv = new TextView(this);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tv.setText(goal);

                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Goals.this);
                            alertDialogBuilder.setMessage("Are you sure, You want to remove it");
                                    alertDialogBuilder.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    getDone_goals().add(tv.getText().toString());
                                                    ll.removeView(tv);
                                                }
                                            });

                            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                            return true;
                        }
                    });

                    ll.addView(tv);
                }
        }

    }

    public void addGoal(View view){
        final LinearLayout ll1 = (LinearLayout)findViewById(R.id.linearLayoutScroll);
        TextView newGoal = findViewById(R.id.goal_input);
        String goal = newGoal.getText().toString();
        if(goal.length() == 0){
            Toast.makeText(getApplicationContext(),"Invalid goal", Toast.LENGTH_SHORT).show();
            return;
        }
        final TextView tv1 = new TextView(this);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        tv1.setText(goal);
        getGoal_list().add(goal);

        tv1.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Goals.this);
                alertDialogBuilder.setMessage("Are you sure, You want to remove it");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                getDone_goals().add(tv1.getText().toString());
                                ll1.removeView(tv1);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });

        ll1.addView(tv1);
        newGoal.setText("");

    }

    public void Homepage(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("ret_done_goals", getDone_goals());
        resultIntent.putExtra("ret_goal_list", getGoal_list());
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
    