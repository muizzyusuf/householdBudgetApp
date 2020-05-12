package com.example.budgeting_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String transactionType;

    ArrayList<String> goal_list;
    ArrayList<String> done_goals;

    public void setGoal_list(ArrayList<String> goal_list) {
        this.goal_list = goal_list;
    }
    public void setDone_goals(ArrayList<String> done_goals) {
        this.done_goals = done_goals;
    }
    public ArrayList<String> getGoal_list() {
        return goal_list;
    }
    public ArrayList<String> getDone_goals() {
        return done_goals;
    }

    ArrayList<String[]> transactionsList;

    public ArrayList<String[]> getTransactionsList() { return transactionsList; }
    public void setTransactionsList(ArrayList<String[]> transactionsList) { this.transactionsList = transactionsList; }

    EditText amt;
    EditText transactionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setGoal_list(new ArrayList<String>());
        setDone_goals(new ArrayList<String>());
        setTransactionsList( new ArrayList<String[]>());

        transactionName = findViewById(R.id.transactionName);
        amt = findViewById(R.id.amount);

        TextView date= findViewById(R.id.textView);
        date.setText(getCurrentDate());

        TextView balanceNum = findViewById(R.id.balance);
        balanceNum.setText("$"+Integer.toString(getCurrentBalance()));

        TextView spent = findViewById(R.id.spent);
        spent.setText("SPENT: $"+Integer.toString(getCurrentSpent()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                setDone_goals(extras.getStringArrayList("ret_done_goals"));
                setGoal_list(extras.getStringArrayList("ret_goal_list"));

            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }

        }else if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                setTransactionsList((ArrayList<String[]>) data.getSerializableExtra("ret_transactionsList"));

            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.expense:
                if (checked) {
                    transactionType = "expense";
                    break;
                }
            case R.id.income:
                if (checked){
                    transactionType = "income";
                    break;
                }
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int getCurrentBalance(){
        int balance = 0;

        for (String[] transactionItem : getTransactionsList()) {
            if(transactionItem[2].equals("expense")) {
                balance -= Integer.parseInt(transactionItem[1]);
            }else if(transactionItem[2].equals("income")){
                balance += Integer.parseInt(transactionItem[1]);
            }
        }
        return balance;
    }

    public int getCurrentSpent(){
        int spent = 0;

        for (String[] transactionItem : getTransactionsList()) {
            if(transactionItem[2].equals("expense")) {
                spent += Integer.parseInt(transactionItem[1]);
            }
        }
        return spent;
    }

    public int getCurrentIncome(){
        int income = 0;

        for (String[] transactionItem : getTransactionsList()) {
            if(transactionItem[2].equals("income")) {
                income += Integer.parseInt(transactionItem[1]);
            }
        }
        return income;
    }

    public void goals(View view) {
        Intent intent= new Intent(this, Goals.class);
        intent.putExtra("done_goals", done_goals);
        intent.putExtra("goal_list", goal_list);
        startActivityForResult(intent, 1);

    }

    public void summary(View view) {
        Intent intent = new Intent(this, Summary.class);
        intent.putExtra("income", getCurrentIncome());
        intent.putExtra("spent", getCurrentSpent());
        intent.putExtra("doneGoals", getDone_goals().size());
        intent.putExtra("totalGoals", getGoal_list().size());
        startActivity(intent);
    }

    public void transactions(View view) {
        Intent intent= new Intent(this, TRANSACTIONS.class);
        intent.putExtra("transactionsList", getTransactionsList());
        startActivityForResult(intent, 2);
    }

    public void users(View view) {
        Intent intent= new Intent(this, Users.class);
        startActivity(intent);
    }

    public void addTransaction(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        String a = transactionName.getText().toString();
        String b = amt.getText().toString();

        if(a.length() == 0){
            Toast.makeText(getApplicationContext(),"Invalid Transaction name", Toast.LENGTH_SHORT).show();
            return;
        }else if (b.length() == 0) {
            Toast.makeText(getApplicationContext(),"Invalid Amount!", Toast.LENGTH_SHORT).show();
            return;
        }else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(),"Select a transaction type!", Toast.LENGTH_SHORT).show();
            return;
        }

        getTransactionsList().add( new String []{a, b, transactionType});

        transactionName.setText("");
        amt.setText("");
        transactionType = "";
        RadioGroup r = findViewById(R.id.radioGroup);
        r.clearCheck();

        TextView balanceNum = findViewById(R.id.balance);
        balanceNum.setText("$"+Integer.toString(getCurrentBalance()));

        TextView spent = findViewById(R.id.spent);
        spent.setText("SPENT: $"+Integer.toString(getCurrentSpent()));

    }
}
