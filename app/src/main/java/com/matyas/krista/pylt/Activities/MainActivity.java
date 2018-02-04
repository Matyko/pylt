package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.matyas.krista.pylt.Calculator.Calculator;
import com.matyas.krista.pylt.Commons.AppColors;
import com.matyas.krista.pylt.Database.AppDatabase;
import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EIObjectDao_Impl;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        GenerateExample.generate();
        loadData();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });

        fab.getDrawable().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
        createPieChart(EIType.OVERALL);
        setValues();

    }

    private void changeActivity() {
        Intent myIntent = new Intent(MainActivity.this, NewItemAcivity.class);
        MainActivity.this.startActivity(myIntent);
    }
    private void createPieChart(EIType type) {
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.clear();
        List<EITag> tags = EITag.getTags();
        List<PieEntry> entries = new ArrayList<>();
        if (type == EIType.OVERALL) {
            entries.add(new PieEntry((Calculator.getEI(EIType.INCOME)), "Income"));
            entries.add(new PieEntry((Calculator.getEI(EIType.EXPENSE)), "Expenses"));
        } else {
            for (int i = 0; i < tags.size(); i++) {
                if (tags.get(i).getType().equals(type))
                    entries.add(new PieEntry(tags.get(i).getAmount(), tags.get(i).getName()));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setValueTextSize(15);
        dataSet.setColors(AppColors.getColors());
        PieData pieData = new PieData(dataSet);
        chart.setData(pieData);
        chart.setEntryLabelColor(Color.DKGRAY);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        if (type == EIType.OVERALL) {
            chart.setCenterText(Long.valueOf((Calculator.getEI(EIType.INCOME)-Calculator.getEI(EIType.EXPENSE))).toString());
        } else {
            chart.setCenterText(Long.valueOf(EIObject.getFullAmount(type)).toString() + "\n" + "Total");
        }
        chart.setCenterTextColor(Color.DKGRAY);
        chart.invalidate();
    }

    private void setValues() {
        TextView balanceText = (TextView) findViewById(R.id.balanceText);
        TextView incomeText = (TextView) findViewById(R.id.incomeText);
        final TextView expensesText = (TextView) findViewById(R.id.expensesText);

        String newBalanceText = balanceText.getText() + " " + Calculator.getBalance();
        balanceText.setText(newBalanceText);
        balanceText.setBackgroundColor(Color.parseColor("#80CBC4"));
        balanceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPieChart(EIType.OVERALL);
            }
        });

        String newIncomeText = incomeText.getText() + " " + Calculator.getEI(EIType.INCOME);
        incomeText.setText(newIncomeText);
        incomeText.setBackgroundColor(Color.parseColor("#B2DFDB"));
        incomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    createPieChart(EIType.INCOME);
            }
        });

        String newExpenseText = expensesText.getText() + " " + Calculator.getEI(EIType.EXPENSE);
        expensesText.setText(newExpenseText);
        expensesText.setBackgroundColor(Color.parseColor("#E0F2F1"));
        expensesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPieChart(EIType.EXPENSE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                EIObject[] eiObjects = adb.eiObject().loadAllObjects();
                for (EIObject eiObject : eiObjects) {
                    EITag.addTag(eiObject.getTag().getName(), eiObject.getAmount(), eiObject.getEitype());
                    new EIObject(eiObject.getName(), eiObject.getEitype(), eiObject.getAmount(), eiObject.getTag());
                }
                return 1;
            }
        }.execute();
    }
}

