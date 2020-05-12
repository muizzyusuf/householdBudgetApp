package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TRANSACTIONS extends AppCompatActivity {
    ArrayList<String[]> transactionsList;

    public ArrayList<String[]> getTransactionsList() {
        return transactionsList;
    }
    public void setTransactionsList(ArrayList<String[]> transactionsList) { this.transactionsList = transactionsList; }

    String [] transName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_r_a_n_s_a_c_t_i_o_n_s);
        Intent intent = getIntent();
        setTransactionsList((ArrayList<String[]>)intent.getSerializableExtra("transactionsList"));

        transName = new String [getTransactionsList().size()];


        for (String[] transactionItem : getTransactionsList()) {
            if(transactionItem[2].equals("expense")){
                final LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutExpense);
                final TextView tv1 = new TextView(this);
                String expense = transactionItem[0]+": $"+ transactionItem[1];
                tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                tv1.setText(expense);

                tv1.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TRANSACTIONS.this);
                        alertDialogBuilder.setMessage("Are you sure, You want to remove it");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        String s = tv1.getText().toString();
                                        for(int i = 0; i < getTransactionsList().size(); i++){

                                            if(s.contains(getTransactionsList().get(i)[0]) && s.contains(getTransactionsList().get(i)[1]));
                                            getTransactionsList().remove(i);
                                        }
                                        ll.removeView(tv1);
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
                ll.addView(tv1);
            }else if(transactionItem[2].equals("income")){
                final LinearLayout ll2 = (LinearLayout)findViewById(R.id.linearLayoutIncome);
                final TextView tv2 = new TextView(this);
                String income = transactionItem[0]+": $"+ transactionItem[1];
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                tv2.setText(income);

                tv2.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TRANSACTIONS.this);
                        alertDialogBuilder.setMessage("Are you sure, You want to remove it");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        String s = tv2.getText().toString();
                                        for(int i = 0; i < getTransactionsList().size(); i++){

                                            if(s.contains(getTransactionsList().get(i)[0]) && s.contains(getTransactionsList().get(i)[1]));
                                            getTransactionsList().remove(i);
                                        }
                                        ll2.removeView(tv2);
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
                ll2.addView(tv2);
            }
        }

    }

    public void Homepage(View view) {
        Intent resultIntent= new Intent();
        resultIntent.putExtra("ret_transactionsList", transactionsList);
        setResult(RESULT_OK, resultIntent);
        finish();

    }
}
