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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.R;

import java.util.ArrayList;
import java.util.List;

public class BalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();

        setContentView(R.layout.balance_main);
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
        setVisibility();
        setValues();

    }

    private void changeActivity() {
        Intent myIntent = new Intent(BalanceActivity.this, NewItemAcivity.class);
        BalanceActivity.this.startActivity(myIntent);
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

    private void setVisibility() {
        View[] views = {findViewById(R.id.edit_balance), findViewById(R.id.edit_income), findViewById(R.id.edit_expenses)};
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
        Button editBalance = (Button) views[0];
        Button editIncome = (Button) views[1];
        Button editExpenses = (Button) views[2];

        editBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, ListViewActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 0);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        editIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, ListViewActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 1);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        editExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, ListViewActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 2);
                intent.putExtras(b);
                startActivity(intent);
                finish();
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
            Intent intent = new Intent(BalanceActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.action_edit) {
            View[] views = {findViewById(R.id.edit_balance), findViewById(R.id.edit_income), findViewById(R.id.edit_expenses)};
            for (View view : views) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                animation.setFillBefore(false);
                view.startAnimation(animation);
                view.setVisibility(View.VISIBLE);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
//                // deletes all table entries
//                adb.eiObject().nukeTable();
                EIObject.getAllObjects().clear();
                EITag.getTags().clear();
                EIObject[] eiObjects = adb.eiObject().loadAllObjects();
                for (EIObject eiObject : eiObjects) {
                    EITag.addTag(eiObject.getTag().getName(), eiObject.getAmount(), eiObject.getEitype());
                }
                return 1;
            }
        }.execute();
    }

    private void createDetailedPieChart(List<EIObject> objects) {
        PieChart chart = (PieChart) findViewById(R.id.chart);
        long sum = 0;
        chart.clear();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            entries.add(new PieEntry(objects.get(i).getAmount(), objects.get(i).getName()));
            sum += objects.get(i).getAmount();
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
        chart.setCenterText(Long.valueOf(sum).toString() + "\n" + "Total");
        chart.setCenterTextColor(Color.DKGRAY);
        chart.invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(BalanceActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

